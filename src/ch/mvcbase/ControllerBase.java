package ch.mvcbase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Base class for all Controllers.
 * <p>
 * The whole application logic is located in controller classes.
 * <p>
 * Controller classes work on and manage the Model. Models encapsulate the whole application state.
 * <p>
 * Controllers provide the whole core functionality of the application, so-called 'Actions'
 * <p>
 * Execution of Actions should be asynchronous. The sequence is kept stable, such that
 * for all actions A and B: if B is submitted after A, B will only be executed after A is finished.
 * <p>
 * use 'async' to wrap all actions
 */
public abstract class ControllerBase<M> {

    // every Controller has its own task handling
    private final ConcurrentTaskQueue<M> actionQueue;

    //needed for audio clips management
    private final ExecutorService soundExecutor;
    private final HashMap<String, Clip> audioClips = new HashMap<>();
    private final HashMap<Clip, SoundPlayerListener> onFinishedHandler = new HashMap<>();

    // The model managed by this Controller. Only subclasses have direct access.
    protected final M model;

    /**
     * The Controller needs a Model.
     *
     * @param model Model managed by this Controller
     */
    protected ControllerBase(M model){
        Objects.requireNonNull(model);

        actionQueue   = new ConcurrentTaskQueue<>();
        soundExecutor = Executors.newFixedThreadPool(4);

        this.model = model;
    }


    public void shutdown(){
       actionQueue.shutdown();
       soundExecutor.shutdown();
    }

    /**
     * If anything needs to be run once at startup from the controller
     */
    public void onStartUp(){}


    /**
     * Schedule the given action for execution in strict order in external thread, asynchronously.
     * <p>
     * onDone is called as soon as action is finished
     */
    protected final void async(Supplier<M> action, Consumer<M> onDone) {
        actionQueue.submit(action, onDone);
    }

    /**
     * Schedule the given action for execution in strict order in external thread, asynchronously.
     *
     */
    protected final void async(Runnable todo) {
        async(() -> { todo.run();
                      return model;
                    },
               m -> {});
    }

    protected void stopSoundClip(String filename){
        Clip clip = getClip(filename);
        clip.stop();
    }

    protected void loopSoundClip(String filename){
        soundExecutor.submit(() -> {
            Clip clip = getClip(filename);
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        });
    }

    protected void playSoundClip(String filename){
        playSoundClip(filename, () -> {});
    }

    protected void playSoundClip(String filename, Runnable onFinished) {
        soundExecutor.submit(() -> {
            Clip clip = getClip(filename);
            clip.removeLineListener(onFinishedHandler.get(clip));
            SoundPlayerListener listener = new SoundPlayerListener(onFinished);
            onFinishedHandler.put(clip, listener);
            clip.addLineListener(listener);
            clip.drain();
            clip.setFramePosition(0);
            clip.start();
        });
    }

    /**
     * Intermediate solution for TestCase support.
     * <p>
     * The best solution would be that 'action' of 'runLater' is executed on calling thread.
     * <p>
     * Waits until all current actions in actionQueue are completed.
     * <p>
     * In most cases, it's wrong to call this method from within an application.
     */
    public void awaitCompletion(){
        if(actionQueue == null){
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        actionQueue.submit( () -> {
            latch.countDown();
            return null;
        });
        try {
            //noinspection ResultOfMethodCallIgnored
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException("CountDownLatch was interrupted");
        }
    }

    /**
     * Only the other base classes 'GuiBase' and 'PuiBase' need access, therefore, it's 'package private'
     */
    M getModel() {
        return model;
    }

    /**
     * Utility function to pause execution of actions for the specified amount of time.
     * <p>
     * An {@link InterruptedException} will be caught and ignored while setting the interrupt flag again.
     *
     * @param duration time to sleep
     */
    protected void pauseExecution(Duration duration) {
        async(() -> {
            try {
                Thread.sleep(duration.toMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    protected Clip getClip(String filename) {
        return audioClips.computeIfAbsent(filename, (key) -> {
            try (BufferedInputStream inputStream = new BufferedInputStream(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)))) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
                Clip audioClip = AudioSystem.getClip();
                audioClip.open(audioStream);
                return audioClip;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean isActionQueueTerminated() {
        return actionQueue.isShutdown();
    }

    public boolean isSoundExecutorTerminated() {
        return soundExecutor.isShutdown();
    }

    private static class SoundPlayerListener implements LineListener {

        private final Runnable onFinished;

        SoundPlayerListener(Runnable onFinished){
            this.onFinished = onFinished;
        }

        @Override
        public void update(LineEvent event) {
            if (LineEvent.Type.STOP == event.getType()) {
                onFinished.run();
            }
        }
    }

}
