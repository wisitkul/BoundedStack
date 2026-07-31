ผู้จัดทำ นาย กรวิชญ์ มาตพรมราช 6821651051 หมู่เรียน 800 แลป 801
      นาย วิศิษฐ์กุล ห้วยหงษ์ทอง 6821651752 หมู่เรียน 800 แลป 801
# BoundedStack

`BoundedStack` เป็น Abstract Data Type (ADT) สำหรับจัดเก็บกล่องพัสดุบนชั้นวาง โดยมีจำนวนกล่องสูงสุดที่วางได้ไม่เกิน 100

กล่องพัสดุทำงานในรูปแบบกล่องที่วางล่าสุดจะอยู่บนสุดและเป็นตัวแรกที่ถูกหยิบออก

## Example

```java
BoundedStack shelf = new BoundedStack();

shelf.push("กล่องพัสดุA");
shelf.push("กล่องพัสดุB");
shelf.push("กล่องพัสดุC");

// Stack: [กล่องพัสดุA, กล่องพัสดุB, กล่องพัสดุC]
// กล่องพัสดุC is top of Stack

shelf.peek();   // -> "กล่องพัสดุC"
```

หลังจากหยิบออก:

```java
shelf.pop();    // -> "กล่องพัสดุC"

// Stack: [กล่องพัสดุA, กล่องพัสดุB]
// กล่องพัสดุB is top of Stack
```

## Design Decisions

### กล่องพัสดุ (Element)

กล่องพัสดุถูกเก็บเป็น `String` เลือกใช้ `String` เพื่อให้โครงสร้างของ ADT ทำได้ง่าย, เร็ว และเน้นการออกแบบพฤติกรรมของ `BoundedStack` เป็นหลัก

### Capacity

ความจุสูงสุดเป็นค่าคงที่ของคลาส ไม่ได้กำหนดตอนสร้างเหมือน ADT อื่น:

```java
public static final int MAX_CAPACITY = 100;
```

ดังนั้นทุก instance ของ `BoundedStack` จะมีความจุสูงสุดเท่ากันคือ 100 กล่อง

### Full Stack

เมื่อ Stack เต็ม (`size() == MAX_CAPACITY`) การ `push()` เพิ่มจะไม่โยน exception แต่จะคืนค่า `false` แทน เพื่อให้ client ตรวจสอบผลลัพธ์และตัดสินใจเองว่าจะจัดการอย่างไรต่อ

### Empty Stack

เมื่อ Stack ว่าง การ `pop()` และ `peek()` จะไม่โยน exception แต่จะคืนค่า `null` แทน

### Null / Empty Checkpoint

ไม่อนุญาตให้ `null` หรือสตริงว่าง `""` เป็นกล่องพัสดุ โดย `push(null)` และ `push("")` จะทำให้เกิด `IllegalArgumentException` เช่นเดียวกับตอนสร้างจาก list เริ่มต้นที่มีสมาชิกเป็น `null` หรือ `""`

### Duplicate Elements

`BoundedStack` **อนุญาต** ให้มีกล่องพัสดุชื่อซ้ำกันได้ ทั้งตอนสร้างจาก list เริ่มต้นและตอน `push()` เพิ่ม (ไม่มีการตรวจสอบความซ้ำ)

### Mutability

`BoundedStack` เป็น Mutable ADT โดย `push()` และ `pop()` สามารถเปลี่ยน state ของ `BoundedStack` เดิมได้

## Operations

### Creator

**`BoundedStack()`**
สร้างชั้นวางกล่องพัสดุว่าง

- Postcondition
  - `size() == 0`
  - `isEmpty() == true`
  - `isFull() == false`

**`BoundedStack(List<String> initial)`**
สร้างชั้นวางกล่องพัสดุจากรายการเริ่มต้น เรียงจากล่างไปบน

- Precondition
  - `initial != null`
  - `initial.size() <= MAX_CAPACITY`
  - ทุกสมาชิกใน `initial` ต้องไม่เป็น `null` และไม่เป็นสตริงว่าง `""`
- Postcondition
  - Stack ใหม่มีกล่องพัสดุตามลำดับเดียวกับ `initial`
  - การแก้ `initial` ภายหลังไม่กระทบ Stack ที่สร้างไว้ (defensive copy)
- Error
  - ผิด precondition ข้อใดก็ตาม -> `IllegalArgumentException`

### Producer

**`BoundedStack shuffled()`**
สร้าง `BoundedStack` ใหม่ที่มีกล่องพัสดุชุดเดียวกัน แต่เรียงลำดับแบบสุ่ม

- Postcondition
  - Stack เดิมไม่เปลี่ยน
  - Stack ใหม่มี `size()` เท่ากับ Stack เดิม
  - Stack ใหม่มีสมาชิกชุดเดียวกับ Stack เดิม แต่ลำดับอาจต่างกัน
  - การแก้ Stack ใหม่ไม่กระทบ Stack เดิม และกลับกัน

### Mutators

**`boolean push(String element)`**
วางกล่องพัสดุใหม่ไว้บนสุดของ Stack

- Precondition
  - `element != null`
  - `element` ไม่เป็นสตริงว่าง `""`
- Postcondition
  - ถ้า Stack ยังไม่เต็ม: `element` กลายเป็น top, `size()` เพิ่มขึ้น 1, คืนค่า `true`
  - ถ้า Stack เต็มแล้ว: Stack ไม่เปลี่ยน, คืนค่า `false`
- Error
  - `element == null` หรือ `element == ""` -> `IllegalArgumentException`

**`String pop()`**
หยิบกล่องพัสดุที่อยู่บนสุดออกจาก Stack แล้วคืนค่า

- Postcondition
  - ถ้า Stack ไม่ว่าง: กล่องที่เป็น top ถูกนำออก, `size()` ลดลง 1, คืนค่ากล่องที่ถูกหยิบออก
  - ถ้า Stack ว่าง: Stack ไม่เปลี่ยน, คืนค่า `null`

### Observers

**`String peek()`**
คืนกล่องพัสดุที่อยู่บนสุดโดยไม่หยิบออก

- Postcondition
  - Stack ไม่เปลี่ยน, `size()` เท่าเดิม
  - ถ้า Stack ว่าง คืนค่า `null`

**`int size()`**
คืนจำนวนกล่องพัสดุที่อยู่บน Stack ในปัจจุบัน

**`boolean isEmpty()`**
คืน `true` เมื่อ Stack ไม่มีกล่องพัสดุ

**`boolean isFull()`**
คืน `true` เมื่อจำนวนกล่องพัสดุเท่ากับ `MAX_CAPACITY`

**`boolean contains(String element)`**
คืน `true` เมื่อมีกล่องพัสดุชื่อ `element` อยู่บน Stack

**`List<String> toList()`**
คืนรายชื่อกล่องพัสดุเรียงจากล่างไปบน เป็น copy ใหม่ทุกครั้งที่เรียก ไม่ใช่ reference ไปยัง representation ภายใน

## Representation

ภายในใช้:

```java
private final List<String> elements;
```

`elements` ใช้เก็บกล่องพัสดุ โดยเรียงจากล่างสุด (index 0) ไปจนถึงบนสุด (index สุดท้าย)

### Abstraction Function

```
AF(elements) = ลำดับของกล่องพัสดุ โดย elements.get(0) คือกล่องล่างสุด
               และ elements.get(elements.size()-1) คือกล่องบนสุด
```

### Representation Invariant

```
elements != null &&
elements.size() <= MAX_CAPACITY &&
ทุกสมาชิกใน elements ไม่เป็น null และไม่เป็นสตริงว่าง ""
```

### Safety from Rep Exposure

- `elements` เป็น `private final` และไม่เคยถูกส่งอ้างอิงออกไปให้ client โดยตรง
- Constructor ที่รับ `List<String>` จะ copy ข้อมูลเข้ามาใหม่ (defensive copy) ก่อนเก็บใน `elements`
- `toList()` คืนค่าเป็น `ArrayList` ใหม่ทุกครั้งที่เรียก ไม่ใช่ `elements` ตัวจริง
- `String` เป็น immutable จึงปลอดภัยที่จะแชร์ตัวอ้างอิงของสมาชิกแต่ละตัวระหว่าง copy ได้
