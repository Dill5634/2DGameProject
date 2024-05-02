package io.github.uoyeng1g6.constants;

import java.util.EnumSet;

/**
 * Enum representing the different types of activities that can be performed by the player.
 */
public enum ActivityType {
    SPOONS,
    PIAZZA,
    GLASSHOUSE,
    BREAKFAST,

    STUDY,
    LIBRARY,
    CSBUILDING,


    RECREATION,
    DUCKS,
    WALK,
    ;

    public static final EnumSet<ActivityType> EAT = EnumSet.of(SPOONS,PIAZZA,GLASSHOUSE);
    public static final EnumSet<ActivityType> WORK = EnumSet.of(STUDY,LIBRARY,CSBUILDING);
    public static final EnumSet<ActivityType> PLAY = EnumSet.of(RECREATION,DUCKS,WALK);
    }
