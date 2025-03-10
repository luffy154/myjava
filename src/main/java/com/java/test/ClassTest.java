package com.java.test;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

/**
 * @ClassName:ClassTest
 * @author: qm
 * @Description:
 * @date:2025-02-10
 */
public class ClassTest {
    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
    }
    public static void main(String[] args) throws Exception {
        // 假设我们有一个类 MyClass，其中有一个方法 targetMethod()
        Class<?> targetClass = Class.forName("com.example.MyClass");
        Method targetMethod = targetClass.getMethod("targetMethod");

        // 创建新的字节码生成器
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        String internalClassName = "com/example/MyClass";
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, internalClassName, null, "java/lang/Object", null);

        // 定义构造函数
        MethodVisitor mvConstructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mvConstructor.visitVarInsn(Opcodes.ALOAD, 0);
        mvConstructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mvConstructor.visitInsn(Opcodes.RETURN);
        mvConstructor.visitMaxs(1, 1);
        mvConstructor.visitEnd();

        // 定义 targetMethod 方法
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "targetMethod", "()I", null, null);
        mv.visitCode();
        // 替换方法逻辑，例如返回一个固定的整数值
        mv.visitInsn(Opcodes.ICONST_1); // 返回1
        mv.visitInsn(Opcodes.IRETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // 结束类的访问
        cw.visitEnd();

        // 获取修改后的字节码
        byte[] classFile = cw.toByteArray();

        // 使用Instrumentation API重新定义类
        if (instrumentation != null) {
            ClassDefinition classDefinition = new ClassDefinition(targetClass, classFile);
            instrumentation.redefineClasses(classDefinition);
        } else {
            System.out.println("Instrumentation not initialized. Cannot redefine class.");
        }

        // 使用反射调用新方法
        Method newMethod = targetClass.getMethod("targetMethod");
        Object instance = targetClass.getDeclaredConstructor().newInstance();
        int result = (int) newMethod.invoke(instance);
        System.out.println("Result of targetMethod: " + result);
    }
}
