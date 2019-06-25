package com.jezz.gc;

public class FinalizeEscapeGC {

    private static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive(){
        System.out.println(" Yes, I am still alive :) ");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(" finalize method excute !");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        // 对象第一次拯救自己
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize的优先级很低,所以暂停0.5秒以等待它
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println(" No , I am dead :(");
        }

        // 下面这段代码与前面相同,但是这次自救失败了
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize的优先级很低,所以暂停0.5秒以等待它
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println(" No , I am dead too :(");
        }
    }
}
