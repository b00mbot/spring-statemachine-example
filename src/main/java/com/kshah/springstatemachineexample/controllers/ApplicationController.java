package com.kshah.springstatemachineexample.controllers;

import com.kshah.springstatemachineexample.model.Events;
import com.kshah.springstatemachineexample.model.Request;
import com.kshah.springstatemachineexample.model.StateMachineHeaders;
import com.kshah.springstatemachineexample.model.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity start(@RequestBody Request request) {
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine();

        // Attach request to state machine
        stateMachine.getExtendedState().getVariables().put(StateMachineHeaders.REQUEST, request);

        // Send START PROCESSING event to state machine
        stateMachine.sendEvent(Events.START_PROCESSING);

        // Send FINISHED PROCESSING event to state machine
        stateMachine.sendEvent(Events.FINISHED_PROCESSING);

        return new ResponseEntity(HttpStatus.OK);
    }

}
