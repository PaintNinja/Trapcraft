package trapcraft.tileentity;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import trapcraft.ModBlocks;

/**
 * @author ProPercivalalb
 * You may look at this file to gain knowledge of javascript
 * but must not copy any features or code directly.
 **/
public class TileEntityBearTrap extends TileEntity implements ITickable {
    
	public EntityLiving entityliving;
    public float prevHealth;
    public float moveSpeed;
    public double posX;
    public double posY;
    public double posZ;
    public Random rand;
    
    public TileEntityBearTrap() {
		super(ModBlocks.TILE_BEAR_TRAP);
        rand = new Random();
	}
    
    @Override
    public void tick() {
        if (entityliving != null) {
            if (entityliving.getDistance((double)posX + 0.5D, (double)posY + 0.20000000000000001D, (double)posZ + 0.5D) > 2D) {
                entityliving = null;
                return;
            }

            entityliving.motionZ = 0.0D;
            entityliving.motionX = 0.0D;
            entityliving.setAIMoveSpeed(0.0F);
            entityliving.motionY = -0.01D;

            if (prevHealth > entityliving.getHealth()) {
            	entityliving.setAIMoveSpeed(this.moveSpeed);
                entityliving = null;
                return;
            }

            if (rand.nextInt(30) == 0) {
                entityliving.attackEntityFrom(DamageSource.IN_WALL, 1);
            }

            prevHealth = entityliving.getHealth();
        }
    }
}
