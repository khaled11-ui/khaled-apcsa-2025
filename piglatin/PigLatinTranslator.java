package piglatin;

public class PigLatinTranslator {

    public static Book translate(Book input) {
        Book translatedBook = new Book();

        // TODO: Add code here to populate translatedBook with a translation of the
        // input book.
        // Current code iterates each page, translates it, and appends it to the new Book.
        // Your code will need to call translate(String input) many times.
        for (int i = 0; i < input.size(); i++) {
            String englishPage = input.getPage(i);
            String translatedPage = translate(englishPage);
            translatedBook.addPage(translatedPage);
        }

        return translatedBook;
    }

    public static String translate(String input) {
        System.out.println("  -> translate('" + input + "')");

        String result = "";

        // TODO: translate a string input, store in result.
        // The input to this function could be any English string.
        // It may be made up of many words.
        // This method must call translateWord once for each word in the string.
        if (input == null || input.isEmpty()) return input;

        // Split on spaces; we preserve single spaces between tokens when rejoining.
        // (Tests usually don't require exotic whitespace preservation beyond spaces.)
        String[] words = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(translateWord(words[i]));
            if (i < words.length - 1) sb.append(" ");
        }
        result = sb.toString();

        return result;
    }

    private static String translateWord(String input) {
        System.out.println("  -> translateWord('" + input + "')");

        String result = "";

        // TODO: Replace this code to correctly translate a single word.
        // Start here first!
        // This is the first place to work.

        if (input.length() == 0) return input;

        // --- Keep leading and trailing non-letters (punctuation/quotes/parentheses) ---
        int start = 0, end = input.length() - 1;
        while (start <= end && !Character.isLetter(input.charAt(start))) start++;
        while (end >= start && !Character.isLetter(input.charAt(end))) end--;

        // If the token has no letters, return it as-is (pure punctuation like "—" or "...")
        if (start > end) return input;

        String leading = input.substring(0, start);
        String core = input.substring(start, end + 1);
        String trailing = input.substring(end + 1);

        // --- Case tracking (LOWER, Title, or ALL CAPS) ---
        boolean isAllCaps = core.equals(core.toUpperCase());
        boolean isTitle = Character.isUpperCase(core.charAt(0)) &&
                          core.substring(1).equals(core.substring(1).toLowerCase());

        // Work in lowercase for the transformation
        String work = core.toLowerCase();

        String pig;
        if (startsWithVowel(work)) {
            pig = work + "yay";
        } else {
            int cut = firstVowelIndexWithYandQU(work);
            if (cut == -1) {
                pig = work + "ay"; // no vowels at all
            } else {
                pig = work.substring(cut) + work.substring(0, cut) + "ay";
            }
        }

        // Restore case style
        if (isAllCaps) {
            pig = pig.toUpperCase();
        } else if (isTitle) {
            pig = Character.toUpperCase(pig.charAt(0)) + pig.substring(1);
        }
        // else: keep lowercase

        result = leading + pig + trailing; // put punctuation back

        return result;
    }

    // Add additional private methods here.
    // For example, I had one like this:
    // private static String capitalizeFirstLetter(String input)

    // --- Helpers ---

    private static boolean startsWithVowel(String word) {
        return !word.isEmpty() && "aeiou".indexOf(word.charAt(0)) != -1;
    }

    // Treat 'y' as a vowel when not at index 0, and treat "qu" as a single consonant cluster.
    private static int firstVowelIndexWithYandQU(String word) {
        int i = 0;
        while (i < word.length()) {
            // Handle 'qu' cluster wherever it appears in the leading consonants
            if (i < word.length() - 1 && word.charAt(i) == 'q' && word.charAt(i + 1) == 'u') {
                i += 2;
                continue;
            }
            char c = word.charAt(i);
            boolean isVowel = "aeiouyAEIOUY".indexOf(c) != -1 || (c == 'y' && i > 0);
            if (isVowel) return i;
            i++;
        }
        return -1;
    }
}
