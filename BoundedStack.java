import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BoundedStack — ADT แทนชั้นวางกล่องพัสดุ
 *
 */
public class BoundedStack {

    public static final int MAX_CAPACITY = 100;

    // ===== representation =====
    private final List<String> elements;

    // AF:
    //   

    // RI:
    //   

    // Safety from rep exposure:
    //   

    private void checkRep() {
        assert elements != null : "";
        assert elements.size() <= MAX_CAPACITY : "";
        for (String e : elements) {
            assert e != null : "";
            assert !e.equals("") : "";
        }
    }

    // ===== Creator =====

    /**
     * สร้างชั้นวางกล่องพัสดุที่ยังไม่มีกล่องเลย
     */
    public BoundedStack() {
        this.elements = new ArrayList<>();
        checkRep();
    }

    /**
     * สร้างชั้นวางกล่องพัสดุจากรายการเริ่มต้น
     */
    public BoundedStack(List<String> initial) {
        if (initial == null)throw new IllegalArgumentException("");
        if (initial.size() > MAX_CAPACITY)throw new IllegalArgumentException("");
        for (String e : initial) {
            if (e == null)throw new IllegalArgumentException("");
            if (e.equals(""))throw new IllegalArgumentException("");
        }

        this.elements = new ArrayList<>(initial);
        checkRep();
    }

    // ===== Mutators =====
    
    /**
     * วางกล่องใหม่ขึ้นไปบนสุด
     */
    public boolean push(String element) {
        if (element == null)throw new IllegalArgumentException("");
        if (element.equals(""))throw new IllegalArgumentException("");
        if (elements.size() == MAX_CAPACITY) {
            return false;
        }
        elements.add(element);
        checkRep();
        return true;
    }

    /**
     * หยิบกล่องที่อยู่บนสุดออกแล้วคืนค่า
     */
    public String pop() {
        if (elements.isEmpty()) {
            return null;
        }
        String top = elements.remove(elements.size() - 1);
        checkRep();
        return top;
    }

    // ===== Observers =====

    /**  */
    public int size() {
        return elements.size();
    }

    /**  */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**  */
    public boolean isFull() {
        return elements.size() == MAX_CAPACITY;
    }

    /**  */
    public boolean contains(String element) {
        return elements.contains(element);
    }

    /**
     */
    public String peek() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(elements.size() - 1);
    }

    /**
     * คืนรายชื่อกล่องบนชั้นเรียงจากล่างไปบน
     */
    public List<String> toList() {
        return new ArrayList<>(elements);
    }

    // ===== Producer =====

    /**
     *แก้
     */
    //public BoundedStack reversed() {
    //    List<String> copy = new ArrayList<>(elements);
    //    Collections.reverse(copy);
    //    return new BoundedStack(copy);
    //}

    //@Override
    //public String toString() {
    //    return elements.toString();
    //}
    
}
