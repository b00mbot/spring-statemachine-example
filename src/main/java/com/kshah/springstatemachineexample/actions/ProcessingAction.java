package com.kshah.springstatemachineexample.actions;

import com.kshah.springstatemachineexample.model.Events;
import com.kshah.springstatemachineexample.model.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;

public class ProcessingAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        System.out.println("Entered state: Processing");
        System.out.println("Target: " + stateContext.getTarget().getId());

        System.out.println("Processing...");

        // Get state machine from context
        StateMachine<States, Events> stateMachine = stateContext.getStateMachine();

        // Send event
        stateMachine.sendEvent(Events.FINISHED_PROCESSING);
    }

}
