/*
 * This file is part of aion-lightning <aion-lightning.org>.
 *
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning. If not, see <http://www.gnu.org/licenses/>.
 */
package quest.crafting;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Gigi
 */
public class _29001ExpertEssencetappingExpert extends QuestHandler {

	private final static int questId = 29001;

	public _29001ExpertEssencetappingExpert() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(204096).addOnQuestStart(questId);
		qe.registerQuestNpc(204096).addOnTalkEvent(questId);
		qe.registerQuestNpc(798800).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204096) {
				if (env.getDialog() == QuestDialog.START_DIALOG) {
					if (giveQuestItem(env, 182207141, 1))
						return sendQuestDialog(env, 1011);
					else
						return true;
				}
				else
					return sendQuestStartDialog(env);
			}
		}

		if (qs == null)
			return false;

		if (qs != null && qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 798800: {
					switch (env.getDialog()) {
						case START_DIALOG:
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return sendQuestDialog(env, 2375);
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798800) {
				if (env.getDialogId() == 34)
					return sendQuestDialog(env, 5);
				else {
					player.getSkillList().addSkill(player, 30002, 400);
					removeQuestItem(env, 182207141, 1);
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}