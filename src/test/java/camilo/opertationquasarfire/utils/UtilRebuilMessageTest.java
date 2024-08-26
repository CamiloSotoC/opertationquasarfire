package camilo.opertationquasarfire.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

class UtilRebuilMessageTest {


    @Test
    void testRebuilMessage_EmptyInput() {
        List<List<String>> emptyInput = new ArrayList<>();
        String result = UtilRebuilMessage.rebuilMessage(emptyInput);
        assertEquals("", result);
    }

    @Test
    void testRebuilMessage_SingleMessage() {
        List<List<String>> singleMessage = new ArrayList<>();
        singleMessage.add(List.of("Hello", "World"));
        String result = UtilRebuilMessage.rebuilMessage(singleMessage);
        assertEquals("Hello World", result);
    }

    @Test
    void testRebuilMessage_MultipleMessages() {
        List<List<String>> multipleMessages = new ArrayList<>();
        multipleMessages.add(List.of("Hello", "World", " Foo"));
        multipleMessages.add(List.of("Hello", "Bar", "Baz"));
        multipleMessages.add(List.of("Hello", "World", "Qux"));
        String result = UtilRebuilMessage.rebuilMessage(multipleMessages);
        assertEquals("Hello World  Foo", result);
    }

    @Test
    void testRebuilMessage_MessagesWithEmptyStrings() {
        List<List<String>> messagesWithEmptyStrings = new ArrayList<>();
        messagesWithEmptyStrings.add(List.of("Hello", "", "World"));
        messagesWithEmptyStrings.add(List.of("Hello", "Bar", ""));
        String result = UtilRebuilMessage.rebuilMessage(messagesWithEmptyStrings);
        assertEquals("Hello Bar World", result);
    }

    @Test
    void testRebuilMessage_MessagesWithDuplicates() {
        List<List<String>> messagesWithDuplicates = new ArrayList<>();
        messagesWithDuplicates.add(List.of("Hello", "World", "Hello"));
        messagesWithDuplicates.add(List.of("Hello", "Bar", "Hello"));
        String result = UtilRebuilMessage.rebuilMessage(messagesWithDuplicates);
        assertEquals("Hello World Hello", result);
    }
}
