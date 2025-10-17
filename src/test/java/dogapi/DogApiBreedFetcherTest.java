package dogapi;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DogApiBreedFetcherTest {

    @Test
    void testValidBreedReturnsSubBreeds()
            throws BreedFetcher.BreedNotFoundException, IOException {
        BreedFetcher fetcher = new DogApiBreedFetcher();
        List<String> subBreeds = fetcher.getSubBreeds("hound");

        // 只检查返回结果包含预期子集，避免 API 新增子品种导致失败
        Set<String> expectedSubset = new HashSet<>(List.of(
                "afghan", "basset", "blood", "english", "ibizan"
        ));
        Set<String> actual = new HashSet<>(subBreeds);

        // hound 应该有一些子品种
        assertFalse(actual.isEmpty(), "Hound should have at least one sub-breed.");
        // 检查是否包含这些已知的子品种
        assertTrue(actual.containsAll(expectedSubset),
                "Returned sub-breeds should include at least the known subset: " + expectedSubset);
    }

    @Test
    void testInvalidBreedThrowsException() {
        BreedFetcher fetcher = new DogApiBreedFetcher();
        assertThrows(BreedFetcher.BreedNotFoundException.class,
                () -> fetcher.getSubBreeds("cat"));
    }
}