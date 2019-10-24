package com.github.unchama.seichiassist.commands

import cats.effect.IO
import com.github.unchama.seichiassist.SeichiAssist
import com.github.unchama.seichiassist.commands.contextual.builder.BuilderTemplates
import com.github.unchama.targetedeffect.MessageEffects._
import org.bukkit.command.TabExecutor

object MapCommand {
  val executor: TabExecutor = BuilderTemplates.playerCommandBuilder
    .execution { context =>
      IO {
        val location = context.sender.getLocation
        val url =
          s"http://map-s${SeichiAssist.seichiAssistConfig.getServerNum}.minecraftserver.jp" +
            s"/?worldname=${location.getWorld.getName}&mapname=flat&zoom=2&" +
            s"x=${location.getBlockX}&y=${location.getBlockY}&z=${location.getBlockZ}"
        url.asMessageEffect()
      }
    }
    .build()
    .asNonBlockingTabExecutor()
}
