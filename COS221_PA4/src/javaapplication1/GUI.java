/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI extends JFrame {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL;
    private String USER;
    private String PASS;


    public GUI() {
        
        Properties props = new Properties();
        try (InputStream in = new FileInputStream("config.properties")) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DB_URL = props.getProperty("dvdrental.DB_PROTO") + "://" +
                 props.getProperty("dvdrental.DB_HOST") + ":" +
                 props.getProperty("dvdrental.DB_PORT") + "/" +
                 props.getProperty("dvdrental.DB_NAME");
        USER = props.getProperty("dvdrental.DB_USERNAME");
        PASS = props.getProperty("dvdrental.DB_PASSWORD");
        
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Staff", createStaffTab());
        tabbedPane.addTab("Films", createFilmsTab());
        tabbedPane.addTab("Report", createReportTab());
        tabbedPane.addTab("Notifications", createNotificationsTab());

        getContentPane().add(tabbedPane);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createStaffTab() {
        JPanel staffPanel = new JPanel();
        staffPanel.setLayout(new BoxLayout(staffPanel, BoxLayout.Y_AXIS));

        String[] columnNames = {"First Name", "Last Name", "Address", "Address2", "District", "City", "Postal Code",
            "Phone", "Store", "Active"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable staffTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(staffTable);
        staffPanel.add(scrollPane);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        JLabel filterLabel = new JLabel("Filter:");
        JTextField filterText = new JTextField(20);
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filter = filterText.getText();
                Connection conn = null;
                Statement stmt = null;
                try {
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    String sql = "SELECT staff.first_name, staff.last_name, address.address, address.address2, address.district, city.city, address.postal_code, address.phone, staff.store_id, staff.active FROM staff JOIN address ON staff.address_id = address.address_id JOIN city ON address.city_id = city.city_id JOIN country ON city.country_id = country.country_id";
                    if (!filter.isEmpty()) {
                        sql += " WHERE first_name LIKE '%" + filter + "%' OR last_name LIKE '%" + filter + "%'";
                    }
                    ResultSet rs = stmt.executeQuery(sql);
                    model.setRowCount(0);
                    while (rs.next()) {
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        String address = rs.getString("address");
                        String address2 = rs.getString("address2");
                        String district = rs.getString("district");
                        String city = rs.getString("city");
                        String postalCode = rs.getString("postal_code");
                        String phone = rs.getString("phone");
                        String store = rs.getString("store_id");
                        boolean active = rs.getBoolean("active");
                        String activeString = active ? "Yes" : "No";
                        model.addRow(new Object[]{firstName, lastName, address, address2, district, city, postalCode,
                            phone, store, activeString});
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (stmt != null) {
                            stmt.close();
                        }
                    } catch (SQLException se2) {
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        });
        filterPanel.add(filterLabel);
        filterPanel.add(filterText);
        filterPanel.add(filterButton);
        staffPanel.add(filterPanel);

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT staff.first_name, staff.last_name, address.address, address.address2, address.district, city.city, address.postal_code, address.phone, staff.store_id, staff.active FROM staff JOIN address ON staff.address_id = address.address_id JOIN city ON address.city_id = city.city_id JOIN country ON city.country_id = country.country_id";
            ResultSet rs = stmt.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                String address2 = rs.getString("address2");
                String district = rs.getString("district");
                String city = rs.getString("city");
                String postalCode = rs.getString("postal_code");
                String phone = rs.getString("phone");
                String store = rs.getString("store_id");
                boolean active = rs.getBoolean("active");
                String activeString = active ? "Yes" : "No";
                model.addRow(new Object[]{firstName, lastName, address, address2, district, city, postalCode,
                    phone, store, activeString});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return staffPanel;
    }

    private JPanel createFilmsTab() {
        JPanel filmsPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Title", "Release Year", "Language", "Rental Duration", "Rental Rate", "Length", "Replacement Cost", "Rating", "Special Features"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable filmsTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(filmsTable);
        filmsPanel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Film");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a popup for adding new data to the table
                JTextField titleField = new JTextField();
                JTextField releaseYearField = new JTextField();
                JTextField languageField = new JTextField();
                JTextField rentalDurationField = new JTextField();
                JTextField rentalRateField = new JTextField();
                JTextField lengthField = new JTextField();
                JTextField replacementCostField = new JTextField();
                JTextField ratingField = new JTextField();
                JTextField specialFeaturesField = new JTextField();
                Object[] fields = {
                    "Title:", titleField,
                    "Release Year:", releaseYearField,
                    "Language:", languageField,
                    "Rental Duration:", rentalDurationField,
                    "Rental Rate:", rentalRateField,
                    "Length:", lengthField,
                    "Replacement Cost:", replacementCostField,
                    "Rating:", ratingField,
                    "Special Features:", specialFeaturesField
                };
                int result = JOptionPane.showConfirmDialog(null, fields, "Add Film", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    int releaseYear = Integer.parseInt(releaseYearField.getText());
                    String language = languageField.getText();
                    int rentalDuration = Integer.parseInt(rentalDurationField.getText());
                    double rentalRate = Double.parseDouble(rentalRateField.getText());
                    int length = Integer.parseInt(lengthField.getText());
                    double replacementCost = Double.parseDouble(replacementCostField.getText());
                    String rating = ratingField.getText();
                    String specialFeatures = specialFeaturesField.getText();
                    Connection conn = null;
                    Statement stmt = null;
                    try {
                        Class.forName(JDBC_DRIVER);
                        conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        stmt = conn.createStatement();
                        String sql;
                        int maxFilmId = 0;
                        ResultSet rs1 = stmt.executeQuery("SELECT MAX(film_id) FROM film");
                        if (rs1.next()) {
                            maxFilmId = rs1.getInt(1);
                        }

                        sql = "INSERT INTO film (film_id, title, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features) VALUES "
                                + "(" + (maxFilmId + 1) + ", '" + title + "', " + releaseYear + ", (SELECT language_id FROM language WHERE name = '" + language + "'), " + rentalDuration + ", " + rentalRate + ", " + length + ", " + replacementCost + ", '" + rating + "', '" + specialFeatures + "')";
                        stmt.executeUpdate(sql);
                        sql = "SELECT film.title, film.release_year, language.name AS language, film.rental_duration, film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features FROM film JOIN language ON film.language_id = language.language_id";
                        ResultSet rs = stmt.executeQuery(sql);
                        model.setRowCount(0);
                        while (rs.next()) {
                            String[] rowData = {
                                rs.getString("title"),
                                Integer.toString(rs.getInt("release_year")),
                                rs.getString("language"),
                                Integer.toString(rs.getInt("rental_duration")),
                                Double.toString(rs.getDouble("rental_rate")),
                                Integer.toString(rs.getInt("length")),
                                Double.toString(rs.getDouble("replacement_cost")),
                                rs.getString("rating"),
                                rs.getString("special_features")
                            };
                            model.addRow(rowData);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (stmt != null) {
                                stmt.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        filmsPanel.add(addButton, BorderLayout.SOUTH);
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT film.title, film.release_year, language.name AS language, film.rental_duration, film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features FROM film JOIN language ON film.language_id = language.language_id";

            ResultSet rs = stmt.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()) {
                String title = rs.getString("title");
                int releaseYear = rs.getInt("release_year");
                String language = rs.getString("language");
                int rentalDuration = rs.getInt("rental_duration");
                double rentalRate = rs.getDouble("rental_rate");
                int length = rs.getInt("length");
                double replacementCost = rs.getDouble("replacement_cost");
                String rating = rs.getString("rating");
                String specialFeatures = rs.getString("special_features");
                model.addRow(new Object[]{title, releaseYear, language, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return filmsPanel;
    }

    private JPanel createReportTab() {
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.Y_AXIS));

        String[] columnNames = {"Store", "Genre", "Number of Movies"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable reportTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        reportPanel.add(scrollPane);

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String storesQuery = "SELECT store_id, address_id FROM store";
            ResultSet storesRs = conn.createStatement().executeQuery(storesQuery);
            while (storesRs.next()) {
                int storeId = storesRs.getInt("store_id");
                int addressId = storesRs.getInt("address_id");

                String genresQuery = "SELECT category.name, COUNT(*) AS movie_count FROM inventory "
                        + "JOIN film_category ON inventory.film_id = film_category.film_id "
                        + "JOIN category ON film_category.category_id = category.category_id "
                        + "WHERE inventory.store_id = " + storeId + " "
                        + "GROUP BY category.name";
                ResultSet genresRs = conn.createStatement().executeQuery(genresQuery);

                List<Object[]> results = new ArrayList<>();

                while (genresRs.next()) {
                    String genre = genresRs.getString("name");
                    int count = genresRs.getInt("movie_count");
                    results.add(new Object[]{storeId, genre, count});
                }

                for (Object[] result : results) {
                    model.addRow(result);
                }
                genresRs.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return reportPanel;
    }

    private JPanel createNotificationsTab() {
        JPanel notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));
        JPanel manageNotificationsPanel = new JPanel(new GridLayout(0, 2));

        JLabel storeIdLabel = new JLabel("Store ID:");
        JTextField storeIdField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel lnameLabel = new JLabel("Surname:");
        JTextField lnameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JButton createButton = new JButton("Create");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton listButton = new JButton("List Clients");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String sName = lnameField.getText();
                String email = emailField.getText();
                String storeID = storeIdField.getText();
                Connection conn = null;
                PreparedStatement stmt = null;

                try {
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    int maxCustomerId = 0;
                    stmt = conn.prepareStatement("SELECT MAX(customer_id) FROM customer");
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        maxCustomerId = rs.getInt(1);
                    }

                    String sql = "INSERT INTO customer (customer_id, store_id, first_name, last_name, email, address_id) "
                            + "VALUES (" + (maxCustomerId + 1) + ", ?, ?, ?, ?, (SELECT address_id FROM store WHERE store_id = ?))";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, storeID);
                    stmt.setString(2, name);
                    stmt.setString(3, sName);
                    stmt.setString(4, email);
                    stmt.setString(5, storeID);
                    stmt.executeUpdate();
                    storeIdField.setText("");
                    nameField.setText("");
                    lnameField.setText("");
                    emailField.setText("");
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (stmt != null) {
                            stmt.close();
                        }
                    } catch (SQLException se2) {
                        se2.printStackTrace();
                    }

                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        });

        manageNotificationsPanel.add(storeIdLabel);
        manageNotificationsPanel.add(storeIdField);
        manageNotificationsPanel.add(nameLabel);
        manageNotificationsPanel.add(nameField);
        manageNotificationsPanel.add(lnameLabel);
        manageNotificationsPanel.add(lnameField);
        manageNotificationsPanel.add(emailLabel);
        manageNotificationsPanel.add(emailField);
        manageNotificationsPanel.add(createButton);
        manageNotificationsPanel.add(updateButton);
        manageNotificationsPanel.add(deleteButton);
        manageNotificationsPanel.add(listButton);

        notificationsPanel.add(manageNotificationsPanel);

        JPanel listClientsPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Surname", "Email"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable clientsTable = new JTable(model);

        listButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT customer_id, first_name, last_name, email FROM customer";
                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        int id = rs.getInt("customer_id");
                        String name = rs.getString("first_name");
                        String sname = rs.getString("last_name");
                        String email = rs.getString("email");
                        Object[] row = {id, name, sname, email};
                        model.addRow(row);
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                JTable table = null;

                try {
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);

                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM customer";
                    rs = stmt.executeQuery(sql);

                    String[] columnNames = {"Customer ID", "First Name", "Last Name", "Email"};
                    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                    while (rs.next()) {
                        Object[] rowData = {rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email")};
                        tableModel.addRow(rowData);
                    }
                    table = new JTable(tableModel);

                    table.setEnabled(true);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    table.setFillsViewportHeight(true);

                    JButton updateButton = new JButton("Update");

                    int option = JOptionPane.showOptionDialog(null, new JScrollPane(table), "Update Customers", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{updateButton}, updateButton);

                    if (option == 0) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) {
                            int customer_id = (int) table.getValueAt(selectedRow, 0);
                            String first_name = (String) table.getValueAt(selectedRow, 1);
                            String last_name = (String) table.getValueAt(selectedRow, 2);
                            String email = (String) table.getValueAt(selectedRow, 3);

                            String updateSql = "UPDATE customers SET first_name=?, last_name=?, email=? WHERE customer_id=?";
                            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                            updateStmt.setString(1, first_name);
                            updateStmt.setString(2, last_name);
                            updateStmt.setString(3, email);
                            updateStmt.setInt(4, customer_id);
                            updateStmt.executeUpdate();
                        }
                    }

                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (stmt != null) {
                            stmt.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = clientsTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?", "Confirm Deletion", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                        int id = (int) clientsTable.getValueAt(selectedRow, 0);

                        String sqlDeletePayment = "DELETE FROM payment WHERE customer_id = ?";
                        PreparedStatement stmtDeletePayment = conn.prepareStatement(sqlDeletePayment);
                        stmtDeletePayment.setInt(1, id);
                        int numPaymentDeleted = stmtDeletePayment.executeUpdate();

                        String sqlDeleteRental = "DELETE FROM rental WHERE customer_id = ?";
                        PreparedStatement stmtDeleteRental = conn.prepareStatement(sqlDeleteRental);
                        stmtDeleteRental.setInt(1, id);
                        int numRentalDeleted = stmtDeleteRental.executeUpdate();

                        String sqlDeleteCustomer = "DELETE FROM customer WHERE customer_id = ?";
                        PreparedStatement stmtDeleteCustomer = conn.prepareStatement(sqlDeleteCustomer);
                        stmtDeleteCustomer.setInt(1, id);
                        int numCustomerDeleted = stmtDeleteCustomer.executeUpdate();

                        if (numCustomerDeleted > 0) {
                            ((DefaultTableModel) clientsTable.getModel()).removeRow(selectedRow);
                            JOptionPane.showMessageDialog(null, "Row deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error deleting row", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error deleting row", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(clientsTable);

        listClientsPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel searchClientsPanel = new JPanel(new BorderLayout());

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField();

        searchClientsPanel.add(searchLabel, BorderLayout.WEST);
        searchClientsPanel.add(searchField, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }

            public void updateTable() {
                String searchText = searchField.getText();
                Connection conn = null;
                Statement stmt = null;

                try {
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    String sql = "SELECT customer_id, first_name, last_name, email FROM customer WHERE first_name LIKE '%" + searchText + "%' OR email LIKE '%" + searchText + "%'";
                    ResultSet rs = stmt.executeQuery(sql);

                    DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
                    model.setRowCount(0);

                    while (rs.next()) {
                        int id = rs.getInt("customer_id");
                        String name = rs.getString("first_name");
                        String sname = rs.getString("last_name");
                        String email = rs.getString("email");
                        model.addRow(new Object[]{id, name, sname, email});
                    }

                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (stmt != null) {
                            stmt.close();
                        }
                    } catch (SQLException se2) {
                        se2.printStackTrace();
                    }

                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        });

        listClientsPanel.add(searchClientsPanel, BorderLayout.NORTH);

        notificationsPanel.add(listClientsPanel);

        return notificationsPanel;
    }

}
