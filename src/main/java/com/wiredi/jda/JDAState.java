package com.wiredi.jda;

import com.wiredi.annotations.Wire;
import com.wiredi.async.StateFull;
import com.wiredi.async.state.State;
import com.wiredi.domain.Eager;
import com.wiredi.runtime.WireRepository;
import com.wiredi.time.Timed;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Wire(proxy = false)
public class JDAState implements StateFull<JDA>, Eager {

    private static final Logger logger = LoggerFactory.getLogger(JDAState.class);
    private final State<JDA> state = State.empty();

    @Override
    public State<JDA> getState() {
        return state;
    }

    @Override
    public void setup(WireRepository wireRepository) {
        try {
            Timed.of(() -> {
                state.set(wireRepository.get(JDA.class).awaitReady());
            }).then((t) -> logger.info("JDA successfully connected in " + t));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void tearDown() {
        Timed.of(() -> state.get().shutdown())
                .then((t) -> logger.info("JDA shutdown in " + t));
    }
}
