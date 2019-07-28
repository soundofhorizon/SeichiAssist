package com.github.unchama.seichiassist.effect.breaking

import com.github.unchama.seichiassist.SeichiAssist
import com.github.unchama.seichiassist.data.PlayerData
import com.github.unchama.seichiassist.effect.AxisAlignedCuboid
import com.github.unchama.seichiassist.effect.XYZTuple
import com.github.unchama.seichiassist.effect.containsBlockAround
import com.github.unchama.seichiassist.effect.forEachGridPoint
import com.github.unchama.seichiassist.util.BreakUtil
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class ExplosionTask(private val player: Player,
                    private val playerdata: PlayerData,
                    private val tool: ItemStack,
                    private val blocks: List<Block>,
                    private val start: XYZTuple,
                    private val end: XYZTuple,
                    private val droploc: Location) : BukkitRunnable() {

  override fun run() {
    AxisAlignedCuboid(start, end).forEachGridPoint(2) { (x, y, z) ->
      val explosionLocation = droploc.clone()
      explosionLocation.add(x.toDouble(), y.toDouble(), z.toDouble())

      if (containsBlockAround(explosionLocation, 1, blocks.toSet())) {
        player.world.createExplosion(explosionLocation, 0f, false)
      }
    }

    val stepflag = playerdata.activeskilldata.skillnum <= 2

    for (block in blocks) {
      BreakUtil.breakBlock(player, block, droploc, tool, stepflag)
      SeichiAssist.allblocklist.remove(block)
    }
  }
}
