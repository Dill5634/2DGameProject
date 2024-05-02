package io.github.uoyeng1g6.constants;

import java.util.EnumSet;

/**
 * Enum representing the different types of activities that can be performed by the player.
 */
public enum ActivityType {
    MEAL,
    STUDY,
    RECREATION,
    DUCKS,
    WALK
    ;

    public static final EnumSet<ActivityType> EAT = EnumSet.of(MEAL);
    public static final EnumSet<ActivityType> WORK = EnumSet.of(STUDY);
    public static final EnumSet<ActivityType> PLAY = EnumSet.of(RECREATION,DUCKS,WALK);
    }
