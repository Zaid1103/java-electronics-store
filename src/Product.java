public abstract class Product {
    private int barcode;
    private String brand;
    private String color;
    private ConnectivityType connectivity;
    private int quantityInStock;
    private double originalCost;
    private double retailPrice;
    private ProductCategory category;

    // Constructor
    public Product(int barcode, String brand, String color, ConnectivityType connectivity, int quantityInStock, double originalCost, double retailPrice, ProductCategory category) {
        this.barcode = barcode;
        this.brand = brand;
        this.color = color;
        this.connectivity = connectivity;
        this.quantityInStock = quantityInStock;
        this.originalCost = originalCost;
        this.retailPrice = retailPrice;
        this.category = category;
    }

    // Getter methods
    public int getBarcode() {
        return barcode;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public ConnectivityType getConnectivity() {
        return connectivity;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public double getOriginalCost() {
        return originalCost;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public ProductCategory getCategory() {
        return category;
    }

    // Setter method
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public abstract String toString();

    
}

