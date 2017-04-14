package me.spoony.botanico.client.graphics.particle;

import com.google.common.collect.Sets;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.buildings.BuildingBreakMaterial;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created by Colten on 12/4/2016.
 */
public class BuildingBreakParticles
{
    Set<BuildingBreakParticle> bbps;
    double homex;
    double homey;

    public BuildingBreakParticles()
    {
        bbps = Sets.newHashSet();
    }

    public void start(TilePosition position, BuildingBreakMaterial mat) {
        homex = position.x;
        homey = position.y;
        Random posRand = new Random();
        synchronized (bbps) {
            for (int i = 0;i<6;i++) {
                double tempx = homex+posRand.nextFloat();
                double tempy = homey+posRand.nextFloat();
                IntRectangle region = mat.getRegion();
                bbps.add(new BuildingBreakParticle(region, tempx, tempy, posRand.nextFloat()*2-1, 1, tempy-posRand.nextFloat()/4f, posRand.nextFloat()));
            }
        }
    }

    public void step(float delta) {
        synchronized (bbps) {
            Iterator<BuildingBreakParticle> iterator = bbps.iterator();
            while (iterator.hasNext())
            {
                BuildingBreakParticle next =  iterator.next();
                next.step(delta);
                if (next.isDead()) iterator.remove();
            }
        }
    }

    public void render(RendererGame rendererGame) {
        synchronized (bbps) {
            Iterator<BuildingBreakParticle> iterator = bbps.iterator();
            while (iterator.hasNext())
            {
                BuildingBreakParticle next =  iterator.next();
                next.render(rendererGame);
            }
        }
    }


    protected class BuildingBreakParticle
    {
        public double x,y;
        public float velx,vely;
        public float life;
        public float maxlife;

        public float grav;

        public double floor;

        public IntRectangle textureregion;

        public BuildingBreakParticle(IntRectangle region, double x, double y, float velx, float vely, double floor, float maxlife)
        {
            this.x = x;
            this.y = y;
            this.velx = velx;
            this.vely = vely;
            this.floor = floor;
            this.maxlife = maxlife;
            this.textureregion = region;

            grav = 5f;
        }

        public void render(RendererGame rg) {
            rg.sprite(new GamePosition(x, y), rg.getResourceManager().getTexture("break_particles.png"), textureregion, y);
        }

        public void step(float delta) {
            life += delta;
            x+=velx*delta;//.add(velocity.x*delta, velocity.y*delta);
            y+=vely*delta;
            vely-=grav*delta;
            if (y <= floor) {
                velx = 0;
                vely = 0;
                grav = 0;
                y = floor;
            }
        }

        public boolean isDead() {
            return life > maxlife;
        }
    }
}
