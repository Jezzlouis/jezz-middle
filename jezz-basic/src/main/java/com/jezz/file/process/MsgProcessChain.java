package com.jezz.file.process;

import java.util.ArrayList;
import java.util.List;

public class MsgProcessChain {
    private List<Process> chains = new ArrayList<>() ;
    /**
     * 添加责任链
     * @param process
     * @return
     */
    public MsgProcessChain addChain(Process process){
        chains.add(process) ;
        return this ;
    }
    /**
     * 执行处理
     * @param msg
     */
    public void process(String msg){
        for (Process chain : chains) {
            chain.doProcess(msg);
        }
    }

}
