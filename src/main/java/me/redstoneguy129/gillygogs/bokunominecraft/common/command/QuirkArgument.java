package me.redstoneguy129.gillygogs.bokunominecraft.common.command;

import com.google.common.collect.Iterables;
import com.ibm.icu.impl.CollectionSet;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class QuirkArgument implements ArgumentType<Quirk> {
    private static final Collection<String> EXAMPLES = Arrays.asList("boku-no-minecraft:quirkless");

    public QuirkArgument() {
    }

    public static QuirkArgument quirk() {
        return new QuirkArgument();
    }

    public static Quirk getQuirk(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, Quirk.class);
    }

    @Override
    public Quirk parse(StringReader reader) throws CommandSyntaxException {
        return Quirk.QUIRK.getValue(ResourceLocation.read(reader));
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    private final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> suggestionHandler = (p_201342_0_, p_201342_1_) -> p_201342_0_.buildFuture();

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (context.getSource() instanceof ISuggestionProvider) {
            StringReader reader = new StringReader(context.getInput());
            return this.suggestionHandler.apply(builder.createOffset(reader.getCursor()), (p_201942_2_) -> {
                Collection<String> collection = new HashSet<>();
                for (Quirk value : Quirk.QUIRK.getValues()) {
                    collection.add(Objects.requireNonNull(value.getRegistryName()).toString());
                }
                ISuggestionProvider.suggest(collection, p_201942_2_);
            });
        } else {
            return Suggestions.empty();
        }
    }
}
