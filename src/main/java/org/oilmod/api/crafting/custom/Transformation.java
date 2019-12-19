package org.oilmod.api.crafting.custom;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Transformation {
    public static final Transformation ReflectionHorizontal=ref(0);
    public static final Transformation ReflectionVertical=ref(90);
    public static final Transformation Rotation90=rot(90);
    public static final Transformation Rotation180=rot(180);
    public static final Transformation Rotation270=rot(270);


    /**
     * @param thetaDegree Allowed values are multiples of 90!
     * @return matrix doing that rotation
     */
    public static Transformation rot(int thetaDegree) {
        Validate.isTrue(Math.abs(thetaDegree)%90==0, "theta must be multiple of 90!");
        double angle =thetaDegree/180d*Math.PI;
        byte a11 = round(Math.cos(angle));
        byte a12 = round(-Math.sin(angle));
        byte a21 = round(Math.sin(angle));
        byte a22 = round(Math.cos(angle));
        return new Transformation(a11, a12, a21, a22);
    }

    /**
     * @param thetaDegree Allowed values are multiples of 45!
     * @return matrix doing that reflection
     */
    public static Transformation ref(int thetaDegree) {
        Validate.isTrue(Math.abs(thetaDegree)%45==0, "theta must be multiple of 45!");
        double angle =thetaDegree/90d*Math.PI;
        byte a11 = round(Math.cos(angle));
        byte a12 = round(Math.sin(angle));
        byte a21 = round(Math.sin(angle));
        byte a22 = round(-Math.cos(angle));
        return new Transformation(a11, a12, a21, a22);
    }

    private static byte round(double in) {
        double off = in%1;
        Validate.inclusiveBetween(-0.05, 0.05, off);
        byte result = (byte)Math.round(in);
        Validate.inclusiveBetween(-1, 1, result);
        return result;
    }


    private final byte a11;
    private final byte a12;
    private final byte a21;
    private final byte a22;


    public Transformation(byte a11, byte a12, byte a21, byte a22) {
        this.a11 = a11;
        this.a12 = a12;
        this.a21 = a21;
        this.a22 = a22;
    }

    public int transformX(int xIn, int yIn) {
        return a11*xIn+a21*yIn;
    }
    public int transformY(int xIn, int yIn) {
        return a12*xIn+a22*yIn;
    }

    public <T> T[][] transform2dArray(T[][] in) {
        return transform2dArrayPrimitive(in, (from, indexFrom, to, indexTo) -> to[indexTo]=from[indexFrom]); //idk about performance impact of that, but no code duplicates yay
    }


    @FunctionalInterface
    public interface PrimitiveArrayAssigner<PrimitiveArray> {
        void copyOver(PrimitiveArray from, int indexFrom, PrimitiveArray to, int indexTo);
    }
    public <PrimitiveArray> PrimitiveArray[] transform2dArrayPrimitive(PrimitiveArray[] in, PrimitiveArrayAssigner<PrimitiveArray> assigner) {
        if (in.length == 0)return in;
        Validate.isTrue(in[0].getClass().isArray(), "PrimitiveArray has to be an array");
        int heightOld = in.length;
        int widthOld = getLength(in[0]); //do not use Array.getLength apparently horrendous performance
        if (widthOld == 0)return in;
        int height = transformX(heightOld, widthOld);
        int width = transformY(heightOld, widthOld);
        int offX = height<0?Math.abs(height)-1:0;
        int offY = width<0?Math.abs(width)-1:0;
        height = Math.abs(height);
        width = Math.abs(width);
        //System.out.printf("heightOld=%d, widthOld=%d, height=%d, width=%d, offX=%d, offY=%d \n", heightOld, widthOld, height, width, offX, offY);

        //noinspection unchecked
        PrimitiveArray[] out = (PrimitiveArray[]) Array.newInstance(in[0].getClass().getComponentType(), height, width);
        for (int i = 0; i < heightOld; i++) {
            for (int j = 0; j < widthOld; j++) {
                int x = offX+transformX(i,j);
                int y = offY+transformY(i,j);
                assigner.copyOver(in[i], j, out[x], y);
            }
        }
        return out;
    }

    //taken from https://bugs.openjdk.java.net/browse/JDK-8051447
    private static int getLength(Object array) {
        // Note: all types of multidimensional arrays are instanceof Object[]
        if (array instanceof Object[]) return ((Object[])array).length;
        if (array instanceof boolean[]) return ((boolean[])array).length;
        if (array instanceof byte[]) return ((byte[])array).length;
        if (array instanceof char[]) return ((char[])array).length;
        if (array instanceof short[]) return ((short[])array).length;
        if (array instanceof int[]) return ((int[])array).length;
        if (array instanceof long[]) return ((long[])array).length;
        if (array instanceof float[]) return ((float[])array).length;
        if (array instanceof double[]) return ((double[])array).length;
        throw badArray(array);
    }
    private static RuntimeException badArray(Object array) {
        if (array == null)
            return new NullPointerException("Array argument is null");
        else if (!array.getClass().isArray())
            return new IllegalArgumentException("Argument is not an array");
        else
            return new IllegalArgumentException("Array is of incompatible type");
    }

    public static void main(String... args) {
        String input =  /*"a\nb";/**/" o            o \n" +
                        "  \\          /  \n" +
                        "   \\        /   \n" +
                        "    :-'\"\"'-:    \n" +
                        " .-'  ____  `-. \n" +
                        "( (  (_()_)  ) )\n" +
                        " `-.   ^^   .-' \n" +
                        "    `._==_.'    \n" +
                        "     __)(___    \n";//*/
        /*"1234\n" +
                "5678\n" +
                "90AB\n" +
                "CDEF";*/
        char[][] chars =Arrays.stream(input.split("\n")).map(String::toCharArray).toArray(char[][]::new);
        Transformation t = rot(90);
        char[][] chars2 = t.transform2dArrayPrimitive(chars, (from, indexFrom, to, indexTo) -> to[indexTo] = from[indexFrom]);


        System.out.println("Input:");
        System.out.println(input);
        StringBuilder sb = new StringBuilder();
        for (char[] inner:chars2) {
            //System.out.printf("got inner array length=%d, array=%s\n", inner.length, Arrays.toString(inner));
            if (inner == null)sb.append("nullarray!");
            for (char c:inner) {
                sb.append(c);
            }
            sb.append('\n');
        }
        System.out.println("Output:");
        System.out.print(sb);
    }
}
