package io.github.uoyeng1g6.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimationComponentTest {
    @Test
    void TestAnimationComponent() {
        AnimationComponent myAnimationComponent = new AnimationComponent(5);
        assertEquals(myAnimationComponent.spriteScale, 5);
    }
}