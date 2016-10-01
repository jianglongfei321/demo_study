package com.roby.demo.jdk.concurrent.unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import sun.misc.Unsafe;
/**
 * Java是一个安全的开发工具，它阻止开发人员犯很多低级的错误，
 * 而大部份的错误都是基于内存管理方面的。如果你想搞破坏，
 * 可以使用Unsafe这个类。这个类是属于sun.* API中的类，
 * 并且它不是J2SE中真正的一部份，因此你可能找不到任何的官方文档，
 * 更可悲的是，它也没有比较好的代码文档。
 * @author jlf
 *
 */
public class UnsafeDemo {  
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {  
        Field f = Unsafe.class.getDeclaredField("theUnsafe"); 
        f.setAccessible(true);  
        Unsafe unsafe = (Unsafe) f.get(null);  
        Player p = (Player) unsafe.allocateInstance(Player.class);  
        System.out.println(p.getAge()); // Print 0  
        p.setAge(45); 
        System.out.println(p.getAge()); // Print 45  
    }  
}  
  
class Player {  
    private int age = 12;  
  
    private Player() {  
        this.age = 50;  
    }  
  
    public int getAge() {  
        return this.age;  
    }  
  
    public void setAge(int age) {  
        this.age = age;  
    }  
}  