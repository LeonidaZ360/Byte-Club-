import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class HavenGUI {

    private static final int MAX_LISTINGS = 50;


    private ArrayList<BoardingHouse> houses = new ArrayList<>();
    private int nextListingID = 1;

    private User student = new User("Student");
    private User admin = new Admin("Admin", "admin123");
    private User currentUser = student;

    
    private JFrame frame = new JFrame("Off-Campus Student Housing Directory");

    
    private JTextField searchField = new JTextField(15);
    private JButton searchButton = new JButton("Search");
    private JButton showAllButton = new JButton("Show All");
    private JButton statsButton = new JButton("Statistics");
    private JButton loginButton = new JButton("Admin Login");
    private JLabel modeLabel = new JLabel();

    
    private JTextArea outputArea = new JTextArea(20, 50);

    
    private JTextField idField = new JTextField(15);
    private JTextField landlordField = new JTextField(15);
    private JTextField phoneField = new JTextField(15);
    private JTextField rentField = new JTextField(15);
    private JTextField distanceField = new JTextField(15);
    private JTextField bedspacesField = new JTextField(15);
    private JTextField occupantsField = new JTextField(15);
    private JTextField descriptionField = new JTextField(15);

    
    private JButton addButton = new JButton("Add Listing");
    private JButton loadButton = new JButton("Load Listing");
    private JButton editButton = new JButton("Edit Listing");
    private JButton removeButton = new JButton("Remove Listing");
    private JButton addOccupantButton = new JButton("Add Occupant");
    private JButton removeOccupantButton = new JButton("Remove Occupant");
    private JButton clearButton = new JButton("Clear Form");

    private JPanel formPanel;    
    private JPanel buttonPanel;  
    private JLabel messageLabel = new JLabel(" ");


    public HavenGUI() {
        frame.setSize(1000, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(outputArea);

        
        frame.add(buildTopPanel(), BorderLayout.NORTH);
        frame.add(buildFormPanel(), BorderLayout.WEST);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(buildSouthPanel(), BorderLayout.SOUTH);

        addListeners();
        setAdminMode(false);
        refresh();

        frame.setVisible(true);
    }

   
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Landlord name:"));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(showAllButton);
        panel.add(statsButton);
        panel.add(loginButton);
        panel.add(modeLabel);
        return panel;
    }

    
    private JPanel buildFormPanel() {
        JPanel form = new JPanel(new GridLayout(8, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Listing form (admin)"));
        form.add(new JLabel("Listing ID:"));
        form.add(idField);
        form.add(new JLabel("Landlord name:"));
        form.add(landlordField);
        form.add(new JLabel("Phone number:"));
        form.add(phoneField);
        form.add(new JLabel("Monthly rent:"));
        form.add(rentField);
        form.add(new JLabel("Distance (km):"));
        form.add(distanceField);
        form.add(new JLabel("Bedspaces:"));
        form.add(bedspacesField);
        form.add(new JLabel("Occupants:"));
        form.add(occupantsField);
        form.add(new JLabel("Description:"));
        form.add(descriptionField);

        
        formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        formPanel.add(form, BorderLayout.NORTH);
        return formPanel;
    }

    private JPanel buildSouthPanel() {
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(addOccupantButton);
        buttonPanel.add(removeOccupantButton);
        buttonPanel.add(clearButton);

        messageLabel.setBorder(BorderFactory.createEmptyBorder(4, 10, 8, 10));

        JPanel south = new JPanel(new BorderLayout());
        south.add(buttonPanel, BorderLayout.NORTH);
        south.add(messageLabel, BorderLayout.SOUTH);
        return south;
    }

  
    
    private void addListeners() {
        searchButton.addActionListener(e -> searchByLandlord());
        showAllButton.addActionListener(e -> {
            searchField.setText("");
            refresh();
            setMessage("Showing all listings.");
        });
        statsButton.addActionListener(e -> showStatistics());
        loginButton.addActionListener(e -> toggleLogin());

        addButton.addActionListener(e -> addListing());
        loadButton.addActionListener(e -> loadListing());
        editButton.addActionListener(e -> editListing());
        removeButton.addActionListener(e -> removeListing());
        addOccupantButton.addActionListener(e -> addOccupant());
        removeOccupantButton.addActionListener(e -> removeOccupant());
        clearButton.addActionListener(e -> clearForm());
    }

    

    
    private void refresh() {
        outputArea.setText("");
        if (houses.isEmpty()) {
            outputArea.setText("No listing has been added yet.");
            return;
        }
        for (BoardingHouse house : houses) {
            outputArea.append(describe(house) + "\n");
        }
    }

    
    private String describe(BoardingHouse h) {
        String status;
        if (h.isAvailable()) {
            status = "Available";
        } else {
            status = "Full";
        }

        return "------------------------------------------------------\n"
                + "Listing ID: " + h.getListingID() + "\n"
                + "Landlord name: " + h.getLandlordName() + "\n"
                + "Phone number: " + h.getPhoneNumber() + "\n"
                + "Rent: " + String.format("%.2f", h.getRentPrice()) + "\n"
                + "Distance: " + h.getDistanceFromCampus() + " km\n"
                + "Max bedspaces: " + h.getMaxBedspace() + "\n"
                + "Total occupants: " + h.getTotalOccupants() + "\n"
                + "Available space: " + h.getAvailableSpace() + "\n"
                + "Status: " + status + "\n"
                + "Description: " + h.getDescription() + "\n";
    }

    private void setMessage(String text) {
        messageLabel.setText(text);
    }

    private void showMessage(String text) {
        JOptionPane.showMessageDialog(frame, text);
    }

    



    private void searchByLandlord() {
        String target = searchField.getText().trim();
        if (target.isEmpty()) {
            showMessage("Enter a landlord name to search.");
            return;
        }

        outputArea.setText("");
        int found = 0;
        for (BoardingHouse house : houses) {
            if (house.getLandlordName().equalsIgnoreCase(target)) {
                outputArea.append(describe(house) + "\n");
                found++;
            }
        }

        if (found == 0) {
            outputArea.setText("No listing found for that landlord.");
        }
        setMessage(found + " listing(s) found for " + target + ".");
    }

    private void showStatistics() {
        if (houses.isEmpty()) {
            showMessage("No listing has been added yet.");
            return;
        }

        double totalRent = 0;
        double totalDistance = 0;
        double minRent = houses.get(0).getRentPrice();
        double maxRent = houses.get(0).getRentPrice();
        double closest = houses.get(0).getDistanceFromCampus();

        for (BoardingHouse house : houses) {
            double rent = house.getRentPrice();
            double distance = house.getDistanceFromCampus();

            totalRent = totalRent + rent;
            totalDistance = totalDistance + distance;

            if (rent < minRent) {
                minRent = rent;
            }
            if (rent > maxRent) {
                maxRent = rent;
            }
            if (distance < closest) {
                closest = distance;
            }
        }

        int count = houses.size();
        String report = "Number of listings: " + count + "\n"
                + "Total rent: " + String.format("%.2f", totalRent) + "\n"
                + "Average rent: " + String.format("%.2f", totalRent / count) + "\n"
                + "Cheapest rent: " + String.format("%.2f", minRent) + "\n"
                + "Most expensive rent: " + String.format("%.2f", maxRent) + "\n"
                + "Average distance: " + String.format("%.2f", totalDistance / count) + " km\n"
                + "Closest listing: " + closest + " km";
        showMessage(report);
    }

    



    private void toggleLogin() {
        if (currentUser == admin) {
            setAdminMode(false);
            setMessage("Logged out of admin mode.");
            return;
        }

        String password = JOptionPane.showInputDialog(frame, "Enter the admin password:");
        if (password == null) {
            return; 
        }

        if (admin.checkPassword(password)) {
            setAdminMode(true);
            setMessage("Logged in as " + admin.getName() + ".");
        } else {
            showMessage("Incorrect password.");
        }
    }

    
    private void setAdminMode(boolean on) {
        if (on) {
            currentUser = admin;
            loginButton.setText("Logout");
        } else {
            currentUser = student;
            loginButton.setText("Admin Login");
        }
        modeLabel.setText("Logged in as: " + currentUser.getName());

        formPanel.setVisible(on);
        buttonPanel.setVisible(on);
        frame.validate();
        frame.repaint();
    }

    
    
    

    private void addListing() {
        if (houses.size() >= MAX_LISTINGS) {
            showMessage("The directory is full.");
            return;
        }

        BoardingHouse bh = readForm(nextListingID);
        if (bh == null) {
            return; 
        }

        houses.add(bh);
        nextListingID = nextListingID + 1;
        clearForm();
        refresh();
        setMessage("Listing added. Listing ID: " + bh.getListingID());
    }

    
    private void loadListing() {
        int index = findListingFromIdField();
        if (index == -1) {
            return;
        }

        BoardingHouse h = houses.get(index);
        landlordField.setText(h.getLandlordName());
        phoneField.setText(h.getPhoneNumber());
        rentField.setText(String.valueOf(h.getRentPrice()));
        distanceField.setText(String.valueOf(h.getDistanceFromCampus()));
        bedspacesField.setText(String.valueOf(h.getMaxBedspace()));
        occupantsField.setText(String.valueOf(h.getTotalOccupants()));
        descriptionField.setText(h.getDescription());
        setMessage("Listing loaded. Change the fields, then click Edit Listing.");
    }

    private void editListing() {
        int index = findListingFromIdField();
        if (index == -1) {
            return;
        }

        BoardingHouse old = houses.get(index);
        BoardingHouse updated = readForm(old.getListingID());
        if (updated == null) {
            return;
        }

        houses.set(index, updated);
        refresh();
        setMessage("Listing " + updated.getListingID() + " updated.");
    }

    private void removeListing() {
        int index = findListingFromIdField();
        if (index == -1) {
            return;
        }

        BoardingHouse h = houses.get(index);
        int answer = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to remove listing " + h.getListingID()
                        + " (" + h.getLandlordName() + ")?",
                "Remove listing", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            houses.remove(index);
            refresh();
            setMessage("Listing removed.");
        } else {
            setMessage("Removal cancelled.");
        }
    }

    private void addOccupant() {
        int index = findListingFromIdField();
        if (index == -1) {
            return;
        }

        if (houses.get(index).addOccupant()) {
            refresh();
            setMessage("Occupant added.");
        } else {
            showMessage("No available space.");
        }
    }

    private void removeOccupant() {
        int index = findListingFromIdField();
        if (index == -1) {
            return;
        }

        if (houses.get(index).removeOccupant()) {
            refresh();
            setMessage("Occupant removed.");
        } else {
            showMessage("There are no occupants to remove.");
        }
    }

    private void clearForm() {
        idField.setText("");
        landlordField.setText("");
        phoneField.setText("");
        rentField.setText("");
        distanceField.setText("");
        bedspacesField.setText("");
        occupantsField.setText("");
        descriptionField.setText("");
    }

    
    
    

    
    private int findListingFromIdField() {
        String text = idField.getText().trim();

        if (!isWholeNumber(text)) {
            showMessage("Enter a valid Listing ID (a whole number).");
            return -1;
        }

        int id = Integer.parseInt(text);
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getListingID() == id) {
                return i;
            }
        }

        showMessage("No listing with that ID.");
        return -1;
    }

    
    private BoardingHouse readForm(int listingID) {
        String landlord = landlordField.getText().trim();
        String phone = phoneField.getText().trim();
        String rentText = rentField.getText().trim();
        String distanceText = distanceField.getText().trim();
        String bedspacesText = bedspacesField.getText().trim();
        String occupantsText = occupantsField.getText().trim();
        String description = descriptionField.getText().trim();

        if (landlord.isEmpty()) {
            showMessage("Landlord name cannot be blank.");
            return null;
        }
        if (phone.isEmpty()) {
            showMessage("Phone number cannot be blank.");
            return null;
        }
        if (!isNumber(rentText)) {
            showMessage("Rent must be a number, for example 850 or 850.50.");
            return null;
        }
        double rent = Double.parseDouble(rentText);
        if (rent <= 0) {
            showMessage("Rent price must be greater than zero.");
            return null;
        }
        if (!isNumber(distanceText)) {
            showMessage("Distance must be a number in km, for example 1.5.");
            return null;
        }
        double distance = Double.parseDouble(distanceText);

        if (!isWholeNumber(bedspacesText)) {
            showMessage("Bedspaces must be a whole number.");
            return null;
        }
        int bedspaces = Integer.parseInt(bedspacesText);
        if (bedspaces < 1) {
            showMessage("Number of bedspaces must be greater than zero.");
            return null;
        }
        if (!isWholeNumber(occupantsText)) {
            showMessage("Occupants must be a whole number (use 0 if empty).");
            return null;
        }
        int occupants = Integer.parseInt(occupantsText);
        if (occupants > bedspaces) {
            showMessage("Total occupants must be between 0 and max bedspaces (" + bedspaces + ").");
            return null;
        }

        if (description.isEmpty()) {
            description = "No description yet";
        }

        return new BoardingHouse(listingID, landlord, phone, rent, distance,
                bedspaces, occupants, description);
    }

    
    private boolean isWholeNumber(String text) {
        if (text.isEmpty() || text.length() > 9) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    
    private boolean isNumber(String text) {
        if (text.isEmpty() || text.length() > 12) {
            return false;
        }
        int dots = 0;
        int digits = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '.') {
                dots++;
            } else if (Character.isDigit(c)) {
                digits++;
            } else {
                return false;
            }
        }
        return dots <= 1 && digits >= 1;
    }

   
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(() -> new HavenGUI());
    }
}
