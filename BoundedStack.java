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
        assert elements != null : "elements must not be null";
        assert elements.size() <= MAX_CAPACITY : "exceeded MAX_CAPACITY";
        for (String e : elements) {
            assert e != null : "element must not be null";
            assert !e.equals("") : "element must not be empty string";
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
     * 
     * @param initial รายการกล่องเริ่มต้นจากล่างไปบนต้องไม่เป็นnull,
     *                ไม่มีกล่องเป็นnullหรือสตริงว่าง และจำนวนต้องไม่เกิน MAX_CAPACITY
     * @throws IllegalArgumentException ถ้า initialผิดเงื่อนไข
     */
    public BoundedStack(List<String> initial) {
        if (initial == null) throw new IllegalArgumentException("initial must not be null");
        if (initial.size() > MAX_CAPACITY) throw new IllegalArgumentException("initial exceeds MAX_CAPACITY");
        for (String e : initial) {
            if (e == null) throw new IllegalArgumentException("element must not be null");
            if (e.equals("")) throw new IllegalArgumentException("element must not be empty string");
        }

        this.elements = new ArrayList<>(initial);
        checkRep();
    }

    // ===== Mutators =====
    /**
     * วางกล่องใหม่ขึ้นไปบนสุด
     * @param element ชื่อกำกับกล่องต้องไม่เป็น nullและสตริงว่าง
     * @return true ถ้าวางสำเร็จ,false ถ้าชั้นเต็มแล้ว
     * @throws IllegalArgumentException ถ้า elementเป็น nullหรือสตริงว่าง
     */
    public boolean push(String element) {
        if (element == null) throw new IllegalArgumentException("element must not be null");
        if (element.equals("")) throw new IllegalArgumentException("element must not be empty string");
        if (elements.size() == MAX_CAPACITY) {
            return false;
        }
        elements.add(element);
        checkRep();
        return true;
    }
    /**
     * หยิบกล่องที่อยู่บนสุดออกแล้วคืนค่า
     * @return กล่องที่เคยอยู่บนสุดก่อนหยิบออกหรือ nullถ้าชั้นว่าง
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
    /** คืนจำนวนกล่องที่อยู่บนชั้นในขณะนี้ */
    public int size() {
        return elements.size();
    }
    /** คืนค่าtrueถ้าชั้นวางไม่มีกล่อง */
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    /** คืนค่าtrueถ้าชั้นวางเต็มความจุ */
    public boolean isFull() {
        return elements.size() == MAX_CAPACITY;
    }
    /** คืนค่าtrueถ้ามีกล่องชื่อelementอยู่บนชั้น */
    public boolean contains(String element) {
        return elements.contains(element);
    }
    /**
     * คืนกล่องที่อยู่บนสุดโดยไม่หยิบออก หรือ nullถ้าชั้นวางว่าง
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
     * สร้างชั้นวางกล่องพัสดุใหม่ที่มีกล่องชุดเดียวกัน
     * แต่เรียงลำดับแบบสุ่มโดยไม่แก้ไขชั้นวางเดิม
     *
     * @return ชั้นวางกล่องพัสดุใหม่ที่มีลำดับกล่องถูกสลับแบบสุ่ม
     */
    public BoundedStack shuffled() {
        List<String> copy = new ArrayList<>(elements);
        Collections.shuffle(copy);
        return new BoundedStack(copy);
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
