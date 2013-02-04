package de.haw_hamburg.ti.tools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintAction implements ActionListener, Printable {

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        if (pageIndex > 0) {    // prints only one page
            return NO_SUCH_PAGE;
        }
        Graphics2D g = (Graphics2D)graphics;
        g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g.drawString("", 100, 100);
        return PAGE_EXISTS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob(); // Get the printer's job
                                                     // list
        job.setPrintable(this); // We print with this class (PrintAction,
                                // which implements Printable)
        if (job.printDialog() == true) { // If we clicked OK in the print dialog
            try {
                job.print();
            } catch (PrinterException ex) {
                System.err.println("Printer has exceptional behavior");
            }
        }

    }

}
