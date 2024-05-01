package io.github.uoyeng1g6.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.uoyeng1g6.constants.GameConstants;
import io.github.uoyeng1g6.models.GameState;

public class CollisionRenderingSystem extends EntitySystem {

    private final static float ppt = GameConstants.PIXELS_PER_TILE;

    public CollisionRenderingSystem(GameState gameState) {

    }

    public static Array<Body> RenderCollisionBodies(TiledMap map, World world) {

        MapObjects objects = map.getLayers().get("Collision").getObjects();

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject){continue;}

            Array<Shape> shapes = new Array<>();

            if (object instanceof RectangleMapObject){
                shapes.add(getRectangle((RectangleMapObject)object));
            }
            else if (object instanceof PolygonMapObject){
                shapes.add(getPolygon((PolygonMapObject)object));
            }
            else if (object instanceof PolylineMapObject){
                shapes.add(getPolyline((PolylineMapObject)object));
            }
            else if (object instanceof CircleMapObject){
                shapes.add(getCircle((CircleMapObject)object));
            }
            else if (object instanceof EllipseMapObject){

                //shapes.addAll(getEllipse((EllipseMapObject)object));

            }
            else {continue;}

            BodyDef bodyD = new BodyDef();
            bodyD.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bodyD);
            bodies.add(body);

            ArrayIterator<Shape> iterator = new ArrayIterator<Shape>(shapes);
            while (iterator.hasNext()) {
                body.createFixture(iterator.next(), 0.0f);
            }
            iterator.forEach(Shape::dispose);
        }
        return bodies;
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                (rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt,
                rectangle.height * 0.5f / ppt,
                size,
                0.0f);
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static Shape[] getEllipse(EllipseMapObject ellipseObject) {

        Ellipse ellipse = ellipseObject.getEllipse();
        float innerRad;
        float outerRad;

        if (ellipse.height >= ellipse.width) {innerRad = ellipse.width; outerRad = ellipse.height;}
        else {innerRad = ellipse.height; outerRad = ellipse.width;}

        // circle part
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(innerRad);
        circleShape.setPosition(new Vector2(ellipse.x / ppt, ellipse.y / ppt));

        Shape[] result = new Shape[1];
        result[0] = circleShape;
        return result;

        /** chain part
        ChainShape chainShape = new ChainShape();
        Vector2[] worldVertices = new Vector2[32];
        int divisions = 32;
        for (int i =0; i < divisions; i++){
            float angle = (float)((Math.PI*2/divisions)*i);

            float x = (float)(outerRad*Math.cos(angle));
            float y = (float)(outerRad*Math.sin(angle));
            assert false;
            worldVertices[i] = (new Vector2(x, y));
        }
        chainShape.createChain(worldVertices);
        Shape[] returnValue = new Shape[2];
        returnValue[0] = chainShape;
        returnValue[1] = circleShape;
        System.out.println("chainShape: " + returnValue[0]);
        System.out.println("circleShape: " + returnValue[1]);
        return returnValue; */
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppt;
            worldVertices[i].y = vertices[i * 2 + 1] / ppt;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;

    }
}
