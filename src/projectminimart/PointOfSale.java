/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectminimart;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
/**
 *
 * @author acer
 */
public class PointOfSale {
            private JTable jTable2;
            private JTextField txttotalPrice;

    public PointOfSale(JTable jTable2, JTextField txttotalPrice) {
        this.jTable2 = jTable2;
        this.txttotalPrice = txttotalPrice;
    }

    public void updateProductQuantityAndClearCart() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    try {
        // อ่านข้อมูลจำนวนสินค้าที่มีอยู่ในไฟล์
        BufferedReader reader = new BufferedReader(new FileReader("C:\\NetBeansProjects\\Java\\ProjectMinimart\\src\\projectminimart\\Admin Product.txt"));
        StringBuilder updatedProductData = new StringBuilder();
        String line;
        boolean productFound = false;
        // วนลูปอ่านข้อมูลจากไฟล์
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            String currentProductName = parts[0];
            int currentQuantity = Integer.parseInt(parts[2]);
            double currentPrice = Double.parseDouble(parts[1]);
            // ตรวจสอบว่าชื่อสินค้าในตะกร้าตรงกับชื่อสินค้าในไฟล์หรือไม่
            for (int i = 0; i < model.getRowCount(); i++) {
                String productNameInCart = (String) model.getValueAt(i, 0);
                int quantityInCart = (int) model.getValueAt(i, 1);
                if (productNameInCart.equals(currentProductName)) {
                    productFound = true;
                    int updatedQuantity = currentQuantity - quantityInCart;
                    // เขียนข้อมูลสินค้าใหม่
                    updatedProductData.append(currentProductName).append(" ").append(currentPrice).append(" ").append(updatedQuantity);
                    // เพิ่ม URL ที่อยู่ใน jLabel1 ลงในบรรทัดเดียวกับข้อมูลสินค้า
                    String imagePath = "/projectminimart/AllPicture/" + currentProductName + ".png";
                    updatedProductData.append(" ").append(imagePath);
                    updatedProductData.append("\n");
                    break;
                }
            }
            // ถ้าไม่มีสินค้าในตะกร้า ให้เขียนข้อมูลสินค้าเดิม
            if (!productFound) {
                updatedProductData.append(line).append("\n");
            }
            // รีเซ็ตค่า productFound เพื่อให้สามารถใช้ในการตรวจสอบสินค้าในรอบถัดไปได้
            productFound = false;
        }
        reader.close();
        // บันทึกข้อมูลใหม่ลงในไฟล์ Admin Product.txt
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\NetBeansProjects\\Java\\ProjectMinimart\\src\\projectminimart\\Admin Product.txt"));
        writer.write(updatedProductData.toString());
        writer.close();
        // ล้างรายการสินค้าในตะกร้า
        model.setRowCount(0);
        // เคลียร์ค่าใน txttotalPrice
        txttotalPrice.setText("");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
    }
}
