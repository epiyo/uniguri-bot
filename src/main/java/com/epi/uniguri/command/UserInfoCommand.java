package com.epi.uniguri.command;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserInfoCommand implements Command {

    @Override
    public String getCommand() {
        return "userinfo";
    }

    @Override
    public Mono<Void> executeCommand(Message eventMessage) {
        Mono<Member> member = eventMessage.getAuthorAsMember();
        return Mono.zip(member, member.flatMapMany(Member::getRoles).collectList())
                .map(TupleUtils.function(this::createEmbedMessage))
                .flatMap(embed -> eventMessage.getChannel()
                .flatMap(channel -> channel.createEmbed(embed)))
                .then();

    }

    private Consumer<EmbedCreateSpec> createEmbedMessage(Member user, List<Role> roles) {
        return embed -> {
            embed.setColor(Color.GREEN)
                    .setAuthor(user.getUsername(), null, user.getAvatarUrl())
                    .addField("Nickname", user.getDisplayName(), false)
                    .addField("ID", user.getId().asString(), false);

            if (!roles.isEmpty()) {
                embed.addField("Roles", roles.stream().map(Role::getMention).collect(Collectors.joining()), true);
            }
        };
    }

}
