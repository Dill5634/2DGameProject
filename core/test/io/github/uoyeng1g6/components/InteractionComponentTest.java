package io.github.uoyeng1g6.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InteractionComponentTest {

    @Test
    void testInteraction()
    {
        InteractionComponent.Interactable mockInteractable = mock(InteractionComponent.Interactable.class);
            InteractionComponent i = new InteractionComponent(mockInteractable);
            assertEquals(mockInteractable, i.interactable);
    }
}
