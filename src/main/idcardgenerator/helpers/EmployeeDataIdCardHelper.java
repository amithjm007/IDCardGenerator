package com.example.demo.helpers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.demo.entity.EmployeeData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;

public class EmployeeDataIdCardHelper {
    private static final String ID_CARD_TEMPLATE_PATH = "src\\main\\resources\\templates\\IdCardTemplate.jpg";
    private static final String UPLOADS_PATH = "uploads\\";
    private static final String IMAGE_FORMAT = ".jpg";

    public static BufferedImage buildIdCard(EmployeeData employee) throws IOException{
        BufferedImage idCardTemplate =ImageIO.read(new File(ID_CARD_TEMPLATE_PATH));
        stampFirmDetails(idCardTemplate, employee.getFirmName());
        stampEmployeeDetails(idCardTemplate, employee);
        stampEmployeePhoto(idCardTemplate, employee.getEmpPhotoId());
        stampEmployeeBarcode(idCardTemplate, employee.getEmpId());
        
        return idCardTemplate;
    }

    private static void stampFirmDetails(BufferedImage idCardTemplate, String firmName){
        Font font = new Font("Calibri", Font.BOLD, 50);
        Graphics graphics = idCardTemplate.getGraphics();
        FontMetrics metrics = graphics.getFontMetrics(font);
        int positionX = (idCardTemplate.getWidth() - metrics.stringWidth(firmName)) / 2;
        int positionY = 125;
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        drawString(firmName, graphics, positionX, positionY);
    }

    private static void stampEmployeeDetails(BufferedImage idCardTemplate, EmployeeData employee){
        stampEmployeeName(idCardTemplate, employee.getEmpName());
        stampEmployeeRole(idCardTemplate, employee.getEmpRole());
        stampEmployeeDetailsSecondary(idCardTemplate, employee);
    }

    private static void stampEmployeeName(BufferedImage idCardTemplate, String employeeName){
        Graphics graphics = idCardTemplate.getGraphics();
        Font font = new Font("Calibri", Font.BOLD, 50);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int positionX = getCentralPosition(idCardTemplate.getWidth(), metrics.stringWidth(employeeName.toUpperCase()));
        int positionY = getCentralPosition(idCardTemplate.getHeight(), metrics.getHeight()) + metrics.getAscent();
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        drawString(employeeName.toUpperCase(), graphics, positionX, positionY + 10);
        graphics.dispose();
    }

    private static void stampEmployeeRole(BufferedImage idCardTemplate, String employeeRole){
        Graphics graphics = idCardTemplate.getGraphics();
        Font font = new Font("Calibri", Font.PLAIN, 30);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int positionX = getCentralPosition(idCardTemplate.getWidth(), metrics.stringWidth(employeeRole));
        int positionY = getCentralPosition(idCardTemplate.getHeight(), metrics.getHeight()) + metrics.getAscent() + 50;
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        drawString(employeeRole, graphics, positionX, positionY);
        graphics.dispose();
    }

    private static void stampEmployeeDetailsSecondary(BufferedImage idCardTemplate, EmployeeData employee){
        String empId   = "Emp ID    :"  + "  " + "77777777";
        String dob     = "DOB        :" + "  " + "07-Dec-96";

        Graphics graphics = idCardTemplate.getGraphics();    
        Font font = new Font("Calibri", Font.ITALIC, 30);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int positionX = (idCardTemplate.getWidth() - metrics.stringWidth(empId)) / 2;
        int positionY = (idCardTemplate.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent() + 150;
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        drawString(empId, graphics, positionX, positionY);
        drawString(dob, graphics, positionX, positionY + 50);
        graphics.dispose();
    }

    private static void stampEmployeePhoto(BufferedImage idCardTemplate, String empPhotoId) throws IOException{
        BufferedImage employeePhoto = ImageIO.read(new File(UPLOADS_PATH + empPhotoId + IMAGE_FORMAT));
        // Scale the image to 200 * 200
        BufferedImage scaledEmpPhoto = getScaledEmpPhoto(employeePhoto, 200, 200);

        Graphics graphics = idCardTemplate.getGraphics();
        int positionX = idCardTemplate.getWidth() / 2 - 100;
        int positionY = 225;
        drawImage(scaledEmpPhoto, graphics, positionX, positionY, null);
        graphics.dispose();
    }

    private static void stampEmployeeBarcode(BufferedImage idCardTemplate, String empId){
        BufferedImage employeeBarcode = getEmployeeBarcode("77777777" + "1234");

        Graphics graphics = idCardTemplate.getGraphics();
        int positionX = getCentralPosition(idCardTemplate.getWidth(), 0) - 200;
        int positionY = getCentralPosition(idCardTemplate.getHeight(), 0) + 300;
        drawImage(employeeBarcode, graphics, positionX, positionY, null);
        graphics.dispose();
    }

    private static BufferedImage getEmployeeBarcode(String empId){
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix;
        try {
            bitMatrix = barcodeWriter.encode(empId, BarcodeFormat.EAN_13, 400, 100);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return new BufferedImage(0, 0, 0);
    }

    private static BufferedImage getScaledEmpPhoto(BufferedImage employeePhoto, int width, int height){
        BufferedImage scaledEmpPhoto = new BufferedImage(200, 200, employeePhoto.getType());
        Graphics2D graphicsForScaling = scaledEmpPhoto.createGraphics();
        graphicsForScaling.drawImage(employeePhoto, 0, 0, 200, 200, null);
        graphicsForScaling.dispose();
        return scaledEmpPhoto;
    }

    private static int getCentralPosition(int imageSize, int fontSize){
        return (imageSize - fontSize) / 2;
    }

    private static void drawString(String stringToDraw, Graphics graphics, int positionX, int positionY){
        graphics.drawString(stringToDraw, positionX, positionY);
    }

    private static void drawImage(BufferedImage imageToDraw, Graphics graphics, int positionX, int positionY, ImageObserver observer){
        graphics.drawImage(imageToDraw, positionX, positionY, observer);
    }
}
