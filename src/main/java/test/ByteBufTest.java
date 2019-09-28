package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static jdk.nashorn.internal.objects.Global.print;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9,100);
        print("allocate ByteBuf(9, 100)", buffer);
        buffer.writeBytes(new byte[]{1,2,3,4});
        print("writeBytes(1,2,3,4)", buffer);

        buffer.writeInt(12);
        print("writeInt(12)", buffer);


    }
}
