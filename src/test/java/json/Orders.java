package json;
import java.util.List;

public class Orders {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<Station> availableStations;

    public List<Order> getOrders() {
        return orders;
    }
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public List<Station> getAvailableStations() {
        return availableStations;
    }
}
