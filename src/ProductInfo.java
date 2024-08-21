public class ProductInfo extends Product{
	private Layout layout;
	private DeviceType type;
    // Constructor taking parameters for all properties
    public ProductInfo(int barcode, String brand, String color, ConnectivityType connectivity, int quantityInStock, double originalCost, double retailPrice, ProductCategory category, Layout layout, DeviceType type) {
        super(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, category);
        this.layout = layout;
        this.type = type;
    }
    
    public Layout getLayout() {
    	return layout;
    }
    
    public DeviceType getType() {
    	return type;
    }

    // Implementing the abstract method toString()
    @Override
    public String toString() {
        return String.format("ProductInfo[barcode=%d, brand=%s, color=%s, connectivity=%s, quantityInStock=%d, originalCost=%.2f, retailPrice=%.2f, category=%s, layout=%s]",
                             getBarcode(), getBrand(), getColor(), getConnectivity(), getQuantityInStock(), getOriginalCost(), getRetailPrice(), getCategory(), getLayout());
    }
}
