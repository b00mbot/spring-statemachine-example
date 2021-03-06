package com.kshah.springstatemachineexample.actions;

import com.kshah.springstatemachineexample.model.Events;
import com.kshah.springstatemachineexample.model.Request;
import com.kshah.springstatemachineexample.model.StateMachineHeaders;
import com.kshah.springstatemachineexample.model.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class EndTransitionAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> stateContext) {

        // Get request from extended state
        Request request = (Request) stateContext.getExtendedState().getVariables().get(StateMachineHeaders.REQUEST);

        log.info("Finished processing request - type " + request.getProcessingType().getType());
    }

}
