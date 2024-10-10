package br.com.schumaker.force.framework.run;

/**
 * The ProgressBar class.
 * This class is responsible for displaying a progress bar.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ProgressBar {
    private final int total;
    private final int barLength;
    private int current;
    private String progressBar;

    /**
     * Constructor.
     *
     * @param total     the total.
     * @param barLength the bar length.
     */
    public ProgressBar(int total, int barLength) {
        this.total = total;
        this.barLength = barLength;
        this.current = 0;
        this.progressBar = "";
    }

    /**
     * Update the progress bar.
     *
     * @param progress the progress.
     * @param message  the message.
     */
    public void update(int progress, String message) {
        current += progress;
        int progressPercentage = (int) ((double) current / total * 100);
        int filledLength = (int) ((double) current / total * barLength);

        String bar = "=".repeat(Math.max(0, filledLength)) + " ".repeat(Math.max(0, barLength - filledLength));

        progressBar = String.format("[%s] %d%% %s", bar, progressPercentage, message);
        display();
    }

    /**
     * Complete the progress bar.
     */
    public void complete() {
        update(total - current, "Complete");
        System.out.println();
    }

    /**
     * Display the progress bar.
     */
    private void display() {
        System.out.print("\r" + progressBar);
    }
}