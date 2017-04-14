package me.spoony.botanico.server.level.levelgen;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.math.IntMath;
import me.spoony.botanico.common.util.BMath;

import java.util.Random;

public class Noise
{
    private final int OffsetBasis = 216613626;
    private final int FnvPrime = 16777619;

    /// <summary>
    ///     A hash lookup table
    /// </summary>
    private int[] perm =
            {
                    151, 160, 137, 91, 90, 15,
                    131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23,
                    190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
                    88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
                    77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
                    102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
                    135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
                    5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
                    223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
                    129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
                    251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
                    49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
                    138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
            };

    /// <summary>
    ///     The used permutation
    /// </summary>
    private static int[] p;

    public int repeat;

    public int seed;

    /// <summary>
    ///     Initalization for Noise
    /// </summary>
    /// <param name="repeat">When to repeat, set to 0 for no repeats (still repeats every 256)</param>
    /// <param name="seed">Seed used for generating the permutation</param>
    public Noise(int repeat, int seed)
    {
        Preconditions.checkArgument(Range.closed(1, 256).contains(repeat), "Repeat must be between 1-256 inclusive");
        this.repeat = repeat == 0 ? 256 : repeat;

        Random shufflerand = new Random(seed);
        shuffleArray(shufflerand, perm);

        p = new int[512];
        for (int x = 0; x < p.length; x++)
            p[x] = perm[x % 256];

        this.seed = seed;
    }

    public float perlin(float x, float y, float z)
    {
        if (repeat > 0)
        {
            x = posMod(x, repeat);
            y = posMod(y, repeat);
            z = posMod(z, repeat);
        }

        int xi = (int) x & 255;
        int yi = (int) y & 255;
        int zi = (int) z & 255;

        float xf = x - (int) x;
        float yf = y - (int) y;
        float zf = z - (int) z;

        float u = fade(xf);
        float v = fade(yf);
        float w = fade(zf);

        int aaa, aba, aab, abb, baa, bba, bab, bbb;
        aaa = p[p[p[xi] + yi] + zi];
        aba = p[p[p[xi] + inc(yi)] + zi];
        aab = p[p[p[xi] + yi] + inc(zi)];
        abb = p[p[p[xi] + inc(yi)] + inc(zi)];
        baa = p[p[p[inc(xi)] + yi] + zi];
        bba = p[p[p[inc(xi)] + inc(yi)] + zi];
        bab = p[p[p[inc(xi)] + yi] + inc(zi)];
        bbb = p[p[p[inc(xi)] + inc(yi)] + inc(zi)];

        float x1, x2, y1, y2;
        x1 = lerp(grad(aaa, xf, yf, zf),
                grad(baa, xf - 1, yf, zf), u);
        x2 = lerp(grad(aba, xf, yf - 1, zf),
                grad(bba, xf - 1, yf - 1, zf), u);
        y1 = lerp(x1, x2, v);

        x1 = lerp(grad(aab, xf, yf, zf - 1),
                grad(bab, xf - 1, yf, zf - 1), u);
        x2 = lerp(grad(abb, xf, yf - 1, zf - 1),
                grad(bbb, xf - 1, yf - 1, zf - 1), u);
        y2 = lerp(x1, x2, v);

        return (float) (lerp(y1, y2, w) + 1) / 2;
    }

    public float octavePerlin(float x, float y, float z, int octaves, float persistence)
    {
        float total = 0;
        float frequency = 1;
        float amplitude = 1;
        float maxValue = 0; // Used for normalizing result to 0.0 - 1.0
        for (int i = 0; i < octaves; i++)
        {
            total += perlin(x * frequency, y * frequency, z * frequency) * amplitude;
            maxValue += amplitude;

            amplitude = persistence * amplitude;
            frequency = 2 * frequency;
        }
        return total / maxValue;
    }

    public float[][] diamondSquareNoise(int offx, int offy, int size)
    {
        Preconditions.checkArgument(IntMath.isPowerOfTwo(size), "The Diamond-Square algorithm requires the size to be a power of 2");

        Random r = new Random((int) seed + offx^4+ offy^2);

        float h = 500.0f;
        final int DATA_SIZE = size + 1;
        float[][] data = new float[DATA_SIZE][DATA_SIZE];

        data[0][0] = 0;
        data[0][size] = 0;
        data[size][0] = 0;
        data[size][size] = 0;

        int iterations = 0;
        for (int sideLength = size; sideLength >= 2; sideLength /= 2, h *= .5f)
        {
            iterations+=h;
            int halfSide = sideLength / 2;

            //square step
            for (int x = 0; x < size; x += sideLength)
            {
                for (int y = 0; y < size; y += sideLength)
                {
                    float avg = data[x][y] + //top left
                            data[x + sideLength][y] +//top right
                            data[x][y + sideLength] + //lower left
                            data[x + sideLength][y + sideLength];//lower right
                    avg /= 4.0;

                    //data[x + halfSide][y + halfSide] = avg + (r.nextFloat() * 2f * h) - h;
                    data[x + halfSide][y + halfSide] = avg + (r.nextFloat() *2* h)-h;
                }
            }

            //diamond step
            for (int x = 0; x < size; x += halfSide)
            {
                for (int y = (x + halfSide) % sideLength; y < size; y += sideLength)
                {
                    float avg =
                            data[(x - halfSide + size) % (size)][y] + //left of center
                                    data[(x + halfSide) % (size)][y] + //right of center
                                    data[x][(y + halfSide) % (size)] + //below center
                                    data[x][(y - halfSide + size) % (size)]; //above center
                    avg /= 4.0;

                    //avg = avg + (r.nextFloat() * 2 * h) - h;
                    avg = avg + (r.nextFloat() *2* h) -h;
                    data[x][y] = avg;

                    if (x == 0) data[size][y] = avg;
                    if (y == 0) data[x][size] = avg;
                }
            }
        }

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                data[x][y] /= ((float)iterations);
            }
        }
        return data;
    }

    public static float fade(float t)
    {
        // Fade function as defined by Ken Perlin.  This eases coordinate values
        // so that they will ease towards integral values.  This ends up smoothing
        // the final output.
        return t * t * t * (t * (t * 6 - 15) + 10); // 6t^5 - 15t^4 + 10t^3
    }

    public int inc(int num) // Returns num + 1, wrapping based on repeat
    {
        num++;
        if (repeat > 0) num %= repeat;

        return num;
    }

    public float grad(int hash, float x, float y, float z)
    {
        switch (hash & 0xF)
        {
            case 0x0:
                return x + y;
            case 0x1:
                return -x + y;
            case 0x2:
                return x - y;
            case 0x3:
                return -x - y;
            case 0x4:
                return x + z;
            case 0x5:
                return -x + z;
            case 0x6:
                return x - z;
            case 0x7:
                return -x - z;
            case 0x8:
                return y + z;
            case 0x9:
                return -y + z;
            case 0xA:
                return y - z;
            case 0xB:
                return -y - z;
            case 0xC:
                return y + x;
            case 0xD:
                return -y + z;
            case 0xE:
                return y - x;
            case 0xF:
                return -y - z;
            default:
                return 0; // never happens
        }
    }

    static void shuffleArray(Random rand, int[] ar)
    {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rand.nextInt(i + 1);

            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


    // Linear Interpolate
    private float lerp(float a, float b, float x)
    {
        return BMath.lerp(a, b, x);
    }

    public float posMod(float x, float m)
    {
        float r = x % m;
        return r < 0 ? r + m : r;
    }
}