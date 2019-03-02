package org.oilmod.api.util;


import org.oilmod.api.rep.particle.ParticleRep;
import org.oilmod.api.rep.stdimpl.world.LocFactoryImpl;
import org.oilmod.api.rep.world.VectorRep;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-Api.
 */
public class ParticleSpawnData {
    private final ParticleRep effect;
    private int id=0;
    private int data=0;
    private float offsetX=0;
    private float offsetY=0;
    private float offsetZ=0;
    private float speed=0;
    private int particleCount=1;
    private int radius=64;

    public ParticleSpawnData(ParticleRep effect) {
        this.effect = effect;
    }

    public ParticleRep getParticle() {
        return effect;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public float getSpeed() {
        return speed;
    }

    public int getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public int getRadius() {
        return radius;
    }

    public VectorRep getOffsetAsVector() {
        return LocFactoryImpl.INSTANCE.createVector(offsetX, offsetY, offsetZ);
    }

    public ParticleSpawnData setData(int data) {
        this.data = data;
        return this;
    }

    public ParticleSpawnData setId(int id) {
        this.id = id;
        return this;
    }

    public ParticleSpawnData setDataAndId(int data, int id) {
        this.data = data;
        this.id = id;
        return this;
    }

    public ParticleSpawnData setOffset(float offsetX, float offsetY, float offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        return this;
    }

    public ParticleSpawnData setOffset(double offsetX, double offsetY, double offsetZ) {
        return this.setOffset((float)offsetX, (float)offsetY, (float)offsetZ);
    }

    public ParticleSpawnData setOffset(int offsetX, int offsetY, int offsetZ) {
        return this.setOffset((float)offsetX, (float)offsetY, (float)offsetZ);
    }

    public ParticleSpawnData setOffset(VectorRep direction) {
        return setOffset(direction.getX(), direction.getY(), direction.getZ());
    }

    public ParticleSpawnData setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public ParticleSpawnData setSpeed(double speed) {
        return this.setSpeed((float)speed);
    }

    public ParticleSpawnData setSpeed(int speed) {
        return this.setSpeed((float)speed);
    }

    public ParticleSpawnData setParticleCount(int particleCount) {
        this.particleCount = particleCount;
        return this;
    }

    public ParticleSpawnData setRadius(int radius) {
        this.radius = radius;
        return this;
    }
}
