package ru.mirea.docker.elitetickets.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import ij.ImagePlus;
import ij.process.ByteBlitter;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

@Slf4j
public class ImageProcessor {

    private static final int NAME_X_POS = 425;
    private static final int NAME_Y_POS = 155;
    private static final int EVENT_X_POS = NAME_X_POS;
    private static final int EVENT_Y_POS = 230;
    private static final int EVENT_ADDRESS_X_POS = EVENT_X_POS;
    private static final int EVENT_ADDRESS_Y_POS = 280;
    private static final int EVENT_DATE_X_POS = 555;
    private static final int EVENT_DATE_Y_POS = 335;
    private static final int EVENT_TIME_X_POS = EVENT_DATE_X_POS;
    private static final int EVENT_TIME_Y_POS = 395;
    private static final int TICKET_PRICE_X_POS = 900;
    private static final int TICKET_PRICE_Y_POS = 370;
    private static final float TICKET_FONT_SIZE_BASE = 64f;

    private static final String RUB_SYMBOL = "₽";

    private static final String REDEEM_URL = "http://localhost:8080/api/v1/tickets/redeem/";

    public BufferedImage processImage(String fileName, TicketModel ticketModel){
        try {

            log.info("create image from {}", fileName);

            ImagePlus img = new ImagePlus(ResourceUtils.getURL(fileName).getPath());

            ij.process.ImageProcessor ip = img.getProcessor();

            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix matrix = writer.encode(REDEEM_URL+ticketModel.getTicketId().toString(),
                    BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage qrcode = MatrixToImageWriter.toBufferedImage(matrix);

            ImagePlus qcode = new ImagePlus();

            qcode.setImage(qrcode);

            ip.copyBits(qcode.getProcessor(), 74, 98, ByteBlitter.COPY);

            String date = ticketModel.getPurchaseDate().toString();

            ip.setFont(ip.getFont().deriveFont(TICKET_FONT_SIZE_BASE / 2));

            ip.drawString(date, (EVENT_DATE_X_POS), (EVENT_DATE_Y_POS));

            String name = ticketModel.getOwnerFirstName() + " " + ticketModel.getOwnerLastName();

            ip.setFont(ip.getFont().deriveFont(TICKET_FONT_SIZE_BASE - ((float) name.length() / 2)));

            ip.drawString(name, NAME_X_POS, NAME_Y_POS);

            String title = ticketModel.getEventName();

            ip.setFont(ip.getFont().deriveFont(TICKET_FONT_SIZE_BASE - title.length()));

            ip.drawString(title, EVENT_X_POS, EVENT_Y_POS);

            String place = "";

            ip.setFont(ip.getFont().deriveFont(TICKET_FONT_SIZE_BASE - place.length()));

            ip.drawString(place, EVENT_ADDRESS_X_POS, EVENT_ADDRESS_Y_POS);

            String time = "";

            ip.setFont(ip.getFont().deriveFont(TICKET_FONT_SIZE_BASE / 2));

            ip.drawString(time, EVENT_TIME_X_POS, EVENT_TIME_Y_POS);

            ip.setFont(ip.getFont().deriveFont(TICKET_FONT_SIZE_BASE - ticketModel.getTicketId().toString().length()));

            ip.drawString(ticketModel.getTicketId().toString(), 425, 75);

            String price = ticketModel.getPrice() + RUB_SYMBOL;

            ip.drawString(price, TICKET_PRICE_X_POS, TICKET_PRICE_Y_POS);

           // byte[] pixels = (byte[]) ip.getPixels();

            return img.getBufferedImage();

        } catch(WriterException e){
            throw new RuntimeException();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
