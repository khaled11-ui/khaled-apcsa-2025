package piglatin;

class PigLatinTranslator {

// Translates an entire Book object to Pig Latin
public static Book translate(Book input) {
    Book translatedBook = new Book();

    System.out.println("Translating book: " + input.getTitle());
    translatedBook.setTitle(input.getTitle() + " (Pig Latin Translation)");

    // Translate each line/page of the book with the existing translate(String)
    int lines = input.getLineCount();
    for (int i = 0; i < lines; i++) {
        String line = input.getLine(i);
        String translatedLine = translate(line);
        translatedBook.appendLine(translatedLine);
    }

    return translatedBook;
}

// Translates a full-length string (page) into Pig Latin
public static String translate(String input) {
    System.out.println("  -> translate('" + input + "')");

        if (input == null || input.isEmpty())
            return input;

        // If the line looks like HTML (contains tags), don't translate it —
        // return it verbatim so markup is preserved.
        if (input.indexOf('<') >= 0 && input.indexOf('>') >= 0) {
            return input;
        }

    String[] words = input.split(" ");
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < words.length; i++) {
        result.append(translateWord(words[i]));
        if (i < words.length - 1)
            result.append(" ");
    }
    return result.toString();
}

// A single word is translated into Pig Latin by
private static String translateWord(String word) {
    if (word == null || word.length() == 0)
        return (word == null) ? null : word;

    // Handle punctuation
    String punctuation = "";
    if (!Character.isLetter(word.charAt(word.length() - 1))) {
        punctuation = word.substring(word.length() - 1);
        word = word.substring(0, word.length() - 1);
        // If removing trailing punctuation produced an empty word (e.g. token was only
        // punctuation), return the punctuation unchanged to avoid index errors below.
        if (word.length() == 0) {
            return punctuation;
        }
    }

    // Find the first vowel
    int vowelIndex = -1;
    for (int i = 0; i < word.length(); i++) {
        char c = Character.toLowerCase(word.charAt(i));
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            vowelIndex = i;
            break;
        }
    }

    // Build Pig Latin word
    String pigLatinWord;
    if (vowelIndex == 0) {
        // Starts with vowel: keep original casing, but add "ay"
        pigLatinWord = word + "ay";
    } else if (vowelIndex > 0) {
        // Move that leading consonant cluster to end.
        // Keep the original casing of the tail (the one going right back to the vowel),
        // but lowercase the moved head (consonant cluster). And finally append "ay".
        String tail = word.substring(vowelIndex); // keep original case
        String head = word.substring(0, vowelIndex).toLowerCase();
        pigLatinWord = tail + head + "ay";
    } else {
        // no vowels: append "ay" to the word
        pigLatinWord = word + "ay";
    }

    // If original word was uppercase, then make sure it starts with uppercase,
    // but do not lowercase the rest; this retains mixed-case tails like “AsH” -> “AsHtray” (supposedly).
    if (Character.isUpperCase(word.charAt(0)) && pigLatinWord.length() > 0) {
        pigLatinWord = Character.toUpperCase(pigLatinWord.charAt(0)) + pigLatinWord.substring(1);
    }

    return pigLatinWord + punctuation;
}
}