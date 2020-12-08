package adventofcode2020.day8;

import lombok.Getter;
import lombok.NonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HandheldHalting {

    public static class Command {

        @Getter
        final CommandType type;
        @Getter
        final int arg;
        @Getter
        final boolean executed;

        private Command(CommandType type, final int arg, boolean executed) {
            this.type = type;
            this.arg = arg;
            this.executed = executed;
        }

        public enum CommandType {
            nop,
            acc,
            jmp,
        }

        public Command copyExecuted() {
            return new Command(this.type, this.arg, true);
        }

        public Command copyToggleNopJmp() {
            CommandType type = (this.type == CommandType.jmp) ?
                    CommandType.nop :
                    (this.type == CommandType.nop ?
                            CommandType.jmp :
                            this.type);
            return new Command(type, this.arg, false);
        }

        public Command copy() {
            return new Command(this.type, this.arg, false);
        }

        private static Pattern pattern = Pattern.compile("(\\w+)\\s([+-]\\d+)");

        public static Command parse(@NonNull String commandStr) {
            Matcher matcher = pattern.matcher(commandStr);
            if (matcher.find()) {
                return new Command(CommandType.valueOf(matcher.group(1)), Integer.parseInt(matcher.group(2)), false);
            }
            throw new IllegalArgumentException("Failed to parse: " + commandStr);
        }
    }

    public static int runCommands(List<Command> commands, int commandIdx, int accumulator) throws InfiniteLoopException {
        if (commands.get(commandIdx).isExecuted())
            throw new InfiniteLoopException(accumulator);
        commands.set(commandIdx, commands.get(commandIdx).copyExecuted());

        if (commandIdx == commands.size() - 1)
            return commands.get(commandIdx).type != Command.CommandType.acc ?
                    accumulator :
                    accumulator+commands.get(commandIdx).arg;

        switch (commands.get(commandIdx).type) {
            case acc:
                return runCommands(commands, commandIdx+1, accumulator+commands.get(commandIdx).arg);
            case jmp:
                return runCommands(commands, commandIdx+commands.get(commandIdx).arg, accumulator);
            case nop:
                return runCommands(commands, commandIdx+1, accumulator);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static List<Command> toggleNopJmp(List<Command> commands, int fixIdx) {
        int idx = 0;
        List<Command> copy = commands.stream().map(Command::copy).collect(Collectors.toList());
        for (int cid = 0; cid < copy.size(); cid++) {
            if (copy.get(cid).type == Command.CommandType.jmp ||
                    copy.get(cid).type == Command.CommandType.nop) {
                if (idx == fixIdx){
                    copy.set(cid, copy.get(cid).copyToggleNopJmp());
                    return copy;
                }
                idx++;
            }
        }

        throw new IllegalStateException();
    }

    public static int fixCommands(List<Command> commands, int fixIdx) {
        List<Command> commandsCopy = toggleNopJmp(commands, fixIdx++);
        try {
            return runCommands(commandsCopy, 0, 0);
        } catch (InfiniteLoopException e) {
            return fixCommands(commands, fixIdx);
        }
    }

    private static List<Command> parseCommands(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<Command> commands = new ArrayList<>();
        while(scanner.hasNextLine())
            commands.add(Command.parse(scanner.nextLine()));

        return commands;
    }

    public static int runBootCode(@NonNull String inputFilePath) throws FileNotFoundException {
        try {
            return runCommands(parseCommands(inputFilePath), 0, 0);
        } catch (InfiniteLoopException ex) {
            return ex.arg;
        }
    }

    public static int fixAndRunBootCode(@NonNull String inputFilePath) throws FileNotFoundException {
        return fixCommands(parseCommands(inputFilePath), 0);
    }
}
