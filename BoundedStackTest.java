import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
 
/**
 * Test runner
 */
public class BoundedStackTest
{
    private static int passed = 0;
    private static int failed = 0;
 
    /**
     * ตัวช่วยกลางสำหรับรันเทสหนึ่งเคส — ตรวจผลแล้วพิมพ์ PASS/FAIL พร้อมนับผลรวมให้อัตโนมัติ
     * @param name ชื่อ/คำอธิบายของเทสเคสที่ต้องการแสดง
     * @param condition ผลของเทส — true ถ้าผ่าน, false ถ้าไม่ผ่าน
     */
    public static void check(String name, boolean condition)
    {
        if (condition)
        {
            passed++;
            System.out.println("[PASS] " + name);
        }
        else
        {
            failed++;
            System.out.println("[Failed] " + name);
        }
    }
public static void main(String[] args)
    {
        testCreators();
        testPush();
        testFullCapacity();
        testPop();
        testPeek();
        testObservers();
        testShuffled();
        testRepExposure();
 
        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");
 
        if (failed > 0)
        {
            System.exit(1);
        }
    }
 private static void testCreators()
    {
        System.out.println("\n-- testCreators --");
 
        BoundedStack empty = new BoundedStack();
        // เช็คว่า no-arg constructor สร้างชั้นวางว่าง size = 0
        check("no-arg constructor -> size==0", empty.size() == 0);
        // เช็คว่า no-arg constructor -> isEmpty() = true
        check("no-arg constructor -> isEmpty()==true", empty.isEmpty());
 
        boolean nullThrows = false;
        try
        {
            new BoundedStack(null);
        }
        catch (IllegalArgumentException e)
        {
            nullThrows = true;
        }
        // เช็คว่าส่ง null เข้า constructor ต้องโยน exception
        check("constructor(null) -> throw IllegalArgumentException", nullThrows);
 
        boolean nullElementThrows = false;
        try
        {
            new BoundedStack(Arrays.asList("A", null, "B"));
        }
        catch (IllegalArgumentException e)
        {
            nullElementThrows = true;
        }
        // เช็คว่า list ที่มีสมาชิกเป็น null ต้องโยน exception
        check("constructor(list with null element) -> throw IllegalArgumentException", nullElementThrows);
 
        boolean emptyStringThrows = false;
        try
        {
            new BoundedStack(Arrays.asList("A", "", "B"));
        }
        catch (IllegalArgumentException e)
        {
            emptyStringThrows = true;
        }
        // เช็คว่า list ที่มีสมาชิกเป็นสตริงว่าง "" ต้องโยน exception
        check("constructor(list with empty string) -> throw IllegalArgumentException", emptyStringThrows);
 
        BoundedStack normal = new BoundedStack(Arrays.asList("text", "book", "notebook"));
        // เช็คว่าสร้างจาก list ที่ถูกต้องแล้ว size และลำดับต้องตรงกับที่ใส่เข้าไป
        check("constructor(valid list) -> preserves size and order",
                normal.toList().equals(Arrays.asList("text", "book", "notebook")));
    }
 
    private static void testPush()
    {
        System.out.println("\n-- testPush --");
 
        BoundedStack s = new BoundedStack();
 
        boolean nullThrows = false;
        try
        {
            s.push(null);
        }
        catch (IllegalArgumentException e)
        {
            nullThrows = true;
        }
        // เช็คว่า push(null) ต้องโยน exception
        check("push(null) -> throw IllegalArgumentException", nullThrows);
 
        boolean emptyThrows = false;
        try
        {
            s.push("");
        }
        catch (IllegalArgumentException e)
        {
            emptyThrows = true;
        }
        // เช็คว่า push("") ต้องโยน exception
        check("push(\"\") -> throw IllegalArgumentException", emptyThrows);
 
        boolean pushed = s.push("pen");
        // เช็คว่า push ของใหม่สำเร็จ คืน true
        check("push(\"pen\") -> return true", pushed);
        // เช็คว่าหลัง push แล้ว size เพิ่มเป็น 1
        check("size==1 after push", s.size() == 1);
    }
 
    private static void testFullCapacity()
    {
        System.out.println("\n-- testFullCapacity --");
 
        BoundedStack s = new BoundedStack();
        for (int i = 0; i < BoundedStack.MAX_CAPACITY; i++)
        {
            s.push("item" + i);
        }
        // เช็คว่า push ครบ MAX_CAPACITY แล้ว isFull() = true
        check("isFull()==true after pushing MAX_CAPACITY items", s.isFull());
 
        boolean pushWhenFull = s.push("overflow");
        // เช็คว่า push ตอนเต็มแล้วต้องคืน false (ไม่โยน exception)
        check("push() when full -> return false", !pushWhenFull);
    }
 
    private static void testPop()
    {
        System.out.println("\n-- testPop --");
 
        BoundedStack a = new BoundedStack(Arrays.asList("pen", "pencil", "Ipad"));
        String popped = a.pop();
        // เช็คว่า pop เอาตัวบนสุด (ตัวท้าย, LIFO) ออกมาจริง
        check("pop() removes and returns the top (LIFO) element", "Ipad".equals(popped));
        // เช็คว่าหลัง pop แล้ว size ลดลง 1
        check("size decreases by 1 after pop", a.size() == 2);
 
        BoundedStack empty = new BoundedStack();
        // เช็คว่า pop() ตอนกล่องว่างคืน null (ไม่โยน exception)
        check("pop() on empty stack -> return null", empty.pop() == null);
    }
 
    private static void testPeek()
    {
        System.out.println("\n-- testPeek --");
 
        BoundedStack s = new BoundedStack(Arrays.asList("pen", "pencil"));
        String top = s.peek();
        // เช็คว่า peek() คืนของบนสุดโดยไม่ลบออก แล้ว size ยังเท่าเดิม
        check("peek() returns top without removing", "pencil".equals(top) && s.size() == 2);
 
        BoundedStack empty = new BoundedStack();
        // เช็คว่า peek() ตอนกล่องว่างคืน null
        check("peek() on empty stack -> return null", empty.peek() == null);
    }
 
    private static void testObservers()
    {
        System.out.println("\n-- testObservers --");
 
        BoundedStack s = new BoundedStack(Arrays.asList("pen", "pencil", "Ipad"));
        // เช็คว่า contains หาของที่มีอยู่จริงเจอ
        check("contains(\"pen\") -> true", s.contains("pen"));
        // เช็คว่า contains หาของที่ไม่มีไม่เจอ
        check("contains(\"clock\") -> false", !s.contains("clock"));
    }
 
    private static void testShuffled()
    {
        System.out.println("\n-- testShuffled --");
 
        BoundedStack origin = new BoundedStack(Arrays.asList("pen", "pencil", "Ipad", "clock"));
        BoundedStack shuffled = origin.shuffled();
 
        // เช็คว่า shuffled มีสมาชิกชุดเดียวกับ origin (เทียบแบบไม่สนลำดับ)
        List<String> a = new ArrayList<>(origin.toList());
        List<String> b = new ArrayList<>(shuffled.toList());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled() contains the same elements as origin", a.equals(b));
 
        // เช็คว่า origin ไม่ถูกแก้ไขหลังเรียก shuffled() (producer ไม่แก้ตัวเดิม)
        check("origin unchanged after shuffled()",
                origin.toList().equals(Arrays.asList("pen", "pencil", "Ipad", "clock")));
    }
 
    private static void testRepExposure()
    {
        System.out.println("\n-- testRepExposure --");
 
        BoundedStack s = new BoundedStack(Arrays.asList("A"));
        List<String> tmp = s.toList();
        tmp.clear();
        tmp.add("B");
 
        // เช็คว่าแก้ list ที่ได้จาก toList() แล้วชั้นวางจริงไม่กระทบ (defensive copy)
        check("toList() returns a defensive copy (mutating it doesn't affect the stack)",
                s.size() == 1 && s.contains("A") && !s.contains("B"));
    }
}
