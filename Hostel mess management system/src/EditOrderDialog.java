import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditOrderDialog extends JDialog {

    private JTextField foodIDField;
    private JTextField quantityField;

    private int orderID;
    private boolean isOrderFinalized;

    public EditOrderDialog(JFrame parent, int orderID) {
        super(parent, true);
        this.orderID = orderID;

        
    }

    private void initializeComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Food ID:"));
        foodIDField = new JTextField(10);
        panel.add(foodIDField);
        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField(10);
        panel.add(quantityField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
        panel.add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    

    private void loadOrderDetails() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel", "root", "aryasql")) {
            String query = "SELECT FoodID, Quantity FROM orders WHERE OrderID = ?";
            PreparedStatement pat = con.prepareStatement(query);
            pat.setInt(1, orderID);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                foodIDField.setText(rs.getString("FoodID"));
                quantityField.setText(rs.getString("Quantity"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateOrder() {
        if (!isOrderFinalized) {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel", "root", "aryasql")) {
                int foodID = Integer.parseInt(foodIDField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                double foodPrice = 20;

                String query = "UPDATE orders SET FoodID=?, Quantity=?, food_price=? WHERE OrderID=?";
                PreparedStatement pat = con.prepareStatement(query);
                pat.setInt(1, foodID);
                pat.setInt(2, quantity);
                pat.setDouble(3, quantity * foodPrice);
                pat.setInt(4, orderID);
                int updatedRows = pat.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Order updated successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update order.");
                }
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error executing SQL query: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                
                        EditOrderDialog editDialog = new EditOrderDialog(frame, 1);
                        editDialog.setVisible(true);
                    
                

                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}


   
