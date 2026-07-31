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
