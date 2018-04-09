package com.kshah.springstatemachineexample.guards;

import com.kshah.springstatemachineexample.model.Events;
import com.kshah.springstatemachineexample.model.Request;
import com.kshah.springstatemachineexample.model.StateMachineHeaders;
import com.kshah.springstatemachineexample.model.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class TypeAProcessingGuard implements Guard<States, Events> {

    @Override
    public boolean evaluate(StateContext<States, Events> stateContext) {

        // Get request from extended state
        Request request = (Request) stateContext.getExtendedState().getVariables().get(StateMachineHeaders.REQUEST);

        if (request == null || request.getProcessingType() != Request.ProcessingType.TYPE_A) {
            return false;
        }

        return true;
    }

}
