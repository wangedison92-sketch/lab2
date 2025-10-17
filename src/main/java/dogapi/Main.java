package dogapi;

import java.io.IOException;

public class Main {

    /**
     * Return the number of sub breeds that the given dog breed has according to the
     * provided fetcher.
     * If the breed does not exist, return 0 instead of throwing an exception.
     *
     * @param breed the name of the dog breed
     * @param breedFetcher the breedFetcher to use
     * @return the number of sub breeds, or 0 if the breed is invalid
     * @throws IOException if a network or IO error occurs
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher)
            throws IOException {
        try {
            return breedFetcher.getSubBreeds(breed).size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: Main <breed>");
            return;
        }

        String breed = args[0];
        BreedFetcher fetcher = new CachingBreedFetcher(new DogApiBreedFetcher());

        try {
            int n = getNumberOfSubBreeds(breed, fetcher);
            if (n == 0) {
                System.out.println("Breed not found: " + breed);
            } else {
                System.out.printf("%s has %d sub-breeds%n", breed, n);
            }
        } catch (IOException e) {
            System.err.println("Network error: " + e.getMessage());
        }
    }
}