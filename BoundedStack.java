import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BoundedStack — ADT แทนชั้นวางกล่องพัสดุ
 * 
 * ค่านามธรรม (A): ลำดับของกล่อง เช่น [กล่องพัสดุA, กล่องพัสดุB, กล่องพัสดุC]
 * 
 *ตัวอย่างการใช้งาน:
 *     BoundedStack shelf = new BoundedStack();
 *     shelf.push("กล่องพัสดุA");
 *     shelf.push("กล่องพัสดุB");
 *     shelf.pop();                 // คืน "กล่องพัสดุB"ที่วางล่าสุด
 *     System.out.println(shelf.size());   // 1
 */
public class BoundedStack {

    public static final int MAX_CAPACITY = 100;

    // ===== representation =====
    private final List<String> elements;

    // AF(elements) = ชั้นวางกล่องพัสดุที่มีกล่องวางซ้อนกันอยู่ โดย elements.get(0)
    // คือกล่องล่างสุด elements.get(elements.size()-1) คือกล่องบนสุด
    //   

    // RI
    // elements != null &&
    // elements.size() <= MAX_CAPACITY &&
    // ทุกสมาชิกใน elements ไม่เป็น null และไม่เป็น string ว่าง ""
    //   

    // Safety from rep exposureelements เป็น private final และไม่เคยถูกส่งอ้างอิงออกไปโดยตรง
    //   - constructor ที่รับ List<String> จะ copy ข้อมูลเข้ามาใหม่
    //   - toList() คืนค่าเป็น copy ใหม่เสมอ ไม่ใช่ elements ตัวจริง
    //   - String เป็น immutable จึงปลอดภัยที่จะแชร์ตัวอ้างอิงของสมาชิกแต่ละตัวได้
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
