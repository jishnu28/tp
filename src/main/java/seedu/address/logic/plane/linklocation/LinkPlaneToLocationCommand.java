package seedu.address.logic.plane.linklocation;

import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.core.Command;
import seedu.address.logic.core.CommandResult;
import seedu.address.logic.core.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.link.exceptions.LinkException;
import seedu.address.model.location.Location;
import seedu.address.model.location.PlaneLocationType;
import seedu.address.model.plane.Plane;

/**
 * The command that links a plane to a flight
 */
public class LinkPlaneToLocationCommand implements Command {
    private static final String DISPLAY_MESSAGE =
            "Linked %s to %s.";

    /**
     * The id of the location
     */
    private final Location location;

    /**
     * The id of the plane
     */
    private final Map<PlaneLocationType, Plane> plane;

    /**
     * Creates a new link command.
     *
     * @param location the id of the location.
     * @param plane the id of the plane.
     */
    public LinkPlaneToLocationCommand(Location location, Map<PlaneLocationType, Plane> plane) {
        this.location = location;
        this.plane = plane;
    }

    @Override
    public String toString() {
        String result = plane.entrySet()
                .stream()
                .map((entry) -> String.format(
                        "%s",
                        entry.getValue().toString()))
                .collect(Collectors.joining(","));
        return String.format(DISPLAY_MESSAGE, result, location.getName());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            for (Map.Entry<PlaneLocationType, Plane> entry : plane.entrySet()) {
                location.getPlaneLink().putRevolve(entry.getKey(), entry.getValue());
            }
        } catch (LinkException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(this.toString());
    }
}