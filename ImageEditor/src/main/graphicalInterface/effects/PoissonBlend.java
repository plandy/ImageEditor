package main.graphicalInterface.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PoissonBlend extends Thread {

    private final WritableImage blendImage;
    private final PixelReader blendImageReader;
    private final PixelWriter blendImageWriter;

    private final Image backgroundImage;
    private final PixelReader backgroundImageReader;

    private double[][] redValues, greenValues, blueValues;
    private double[][] redValuesGuess, greenValuesGuess, blueValuesGuess;
    private double[][] gradientRed, gradientGreen, gradientBlue;

    private short[][] mask;
    private short BACKGROUND_PIXEL = 0, BORDER_PIXEL = 1, INTERIOR_PIXEL = 2;

    private ImageBoundingBox blendImageBounds;

    private final int imageWidth, imageHeight;

    private volatile boolean isFinished = false;

    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Takes 2 images: foreground and background. Both must be the same dimensions.
     * Foreground image must have transparent pixels around the region of interest
     * <br>
     * Blends foreground onto background.
     *
     * @param p_blendImage
     * @param p_backgroundImage
     */
    public PoissonBlend( WritableImage p_blendImage, Image p_backgroundImage ) {

        blendImage = p_blendImage;
        blendImageReader = blendImage.getPixelReader();
        blendImageWriter = blendImage.getPixelWriter();

        backgroundImage = p_backgroundImage;
        backgroundImageReader = backgroundImage.getPixelReader();

        imageWidth = (int)blendImage.getWidth();
        imageHeight = (int)blendImage.getHeight();
    }

    @Override
    public void run() {
        preProcess();
        poissonBlend();
        finish();
    }

    private void finish() {
        isFinished = true;
    }

    private void poissonBlend() {
        double error = 0.0;
        double lastError = 0.0;
        int count = 0;

        long startTime = System.currentTimeMillis();

        do {
            for ( int i = 0; i < 50; i++ ) {
                //jacobi_Iteration();
                //gaussSeidel_Iteration();
                gaussSeidel_WithSOR_Iteration();

                count++;
            }
            lastError = error;
            error = calculateError();
            count++;
            System.out.println("error = " + error);
            //System.out.println("errorDiff = " + (error - lastError));
            //} while ( Math.abs((lastError - error)) > 0.0001 );
        } while ( error > 1.0 );
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("execution time seconds = " + duration);
        System.out.println("error = " + error);
        System.out.println("count = " + count);

        normalizePixelValues();
        writePixelValues();

        System.out.println("finished");
    }

    private void jacobi_Iteration() {
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {
                    redValuesGuess[x][y] = gradientRed[x][y];
                    greenValuesGuess[x][y] = gradientGreen[x][y];
                    blueValuesGuess[x][y] = gradientBlue[x][y];
                    //=========================
                    if ( mask[x+1][y] == INTERIOR_PIXEL ) {

                        redValuesGuess[x][y] += redValues[x+1][y];
                        greenValuesGuess[x][y] += greenValues[x+1][y];
                        blueValuesGuess[x][y] += blueValues[x+1][y];
                    }
                    //=========================
                    if ( mask[x-1][y] == INTERIOR_PIXEL ) {
                        redValuesGuess[x][y] += redValues[x-1][y];
                        greenValuesGuess[x][y] += greenValues[x-1][y];
                        blueValuesGuess[x][y] += blueValues[x-1][y];
                    }
                    //=========================
                    if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        redValuesGuess[x][y] += redValues[x][y+1];
                        greenValuesGuess[x][y] += greenValues[x][y+1];
                        blueValuesGuess[x][y] += blueValues[x][y+1];
                    }
                    //=========================
                    if ( mask[x][y-1] == INTERIOR_PIXEL ) {
                        redValuesGuess[x][y] += redValues[x][y-1];
                        greenValuesGuess[x][y] += greenValues[x][y-1];
                        blueValuesGuess[x][y] += blueValues[x][y-1];
                    }

                    redValuesGuess[x][y] /= 4;
                    greenValuesGuess[x][y] /= 4;
                    blueValuesGuess[x][y] /= 4;

//					redValues[x][y] = redValuesGuess[x][y] * 0.25;
//					greenValues[x][y] = greenValuesGuess[x][y] * 0.25;
//					blueValues[x][y] = blueValuesGuess[x][y] * 0.25;

                }
            }
        }
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {
                    redValues[x][y] = redValuesGuess[x][y];
                    greenValues[x][y] = greenValuesGuess[x][y];
                    blueValues[x][y] = blueValuesGuess[x][y];
                }
            }
        }

    }

    private void gaussSeidel_Iteration() {
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {

                    redValues[x][y] = gradientRed[x][y];
                    greenValues[x][y] = gradientGreen[x][y];
                    blueValues[x][y] = gradientBlue[x][y];


                    //=========================
                    if ( mask[x+1][y] == INTERIOR_PIXEL ) {

                        redValues[x][y] += redValues[x+1][y];
                        greenValues[x][y] += greenValues[x+1][y];
                        blueValues[x][y] += blueValues[x+1][y];
                    }
                    //=========================
                    if ( mask[x-1][y] == INTERIOR_PIXEL ) {
                        redValues[x][y] += redValues[x-1][y];
                        greenValues[x][y] += greenValues[x-1][y];
                        blueValues[x][y] += blueValues[x-1][y];
                    }
                    //=========================
                    if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        redValues[x][y] += redValues[x][y+1];
                        greenValues[x][y] += greenValues[x][y+1];
                        blueValues[x][y] += blueValues[x][y+1];
                    }
                    //=========================
                    if ( mask[x][y-1] == INTERIOR_PIXEL ) {
                        redValues[x][y] += redValues[x][y-1];
                        greenValues[x][y] += greenValues[x][y-1];
                        blueValues[x][y] += blueValues[x][y-1];
                    }

                    redValues[x][y] = redValues[x][y] * 0.25;
                    greenValues[x][y] = greenValues[x][y] * 0.25;
                    blueValues[x][y] = blueValues[x][y] * 0.25;

                }
            }
        }

    }

    private void gaussSeidel_WithSOR_Iteration() {

        double pi = 3.1415926535897932384626433832795;

        //double weight = 2 / (1+ Math.sin(2*pi/count));
        //double h = 1/(count+1);
        //double weight = 2 / (1+ Math.sin(l_pi*count)); /**http://userpages.umbc.edu/~gobbert/papers/YangGobbertAML2007.pdf*/
        //double weight = 2 / (1+ Math.sin(l_pi/count));
        double dimensionLength = 0;
        if ( imageWidth > imageHeight ) {
            dimensionLength = imageHeight;
        } else {
            dimensionLength = imageWidth;
        }
        double spectralRadius = pi / (1 + dimensionLength);
        double weight = 2 / ( 1 + Math.sqrt( (spectralRadius*spectralRadius) ) );

        //weight = 1.4;
        //System.out.println("optimal parameter = " + weight);
        double minusWeight = 1 - weight;
        //System.out.println("optimal weight = "+weight);

        double oldRed;
        double oldGreen;
        double oldBlue;

        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                    if ( mask[x][y] == INTERIOR_PIXEL ) {

                    oldRed = redValues[x][y];
                    oldGreen = greenValues[x][y];
                    oldBlue = blueValues[x][y];

                    redValues[x][y] = gradientRed[x][y];
                    greenValues[x][y] = gradientGreen[x][y];
                    blueValues[x][y] = gradientBlue[x][y];

                    //=========================
                    if ( mask[x+1][y] == INTERIOR_PIXEL ) {

                        redValues[x][y] += redValues[x+1][y];
                        greenValues[x][y] += greenValues[x+1][y];
                        blueValues[x][y] += blueValues[x+1][y];
                    }
                    //=========================
                    if ( mask[x-1][y] == INTERIOR_PIXEL ) {
                        redValues[x][y] += redValues[x-1][y];
                        greenValues[x][y] += greenValues[x-1][y];
                        blueValues[x][y] += blueValues[x-1][y];
                    }
                    //=========================
                    if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        redValues[x][y] += redValues[x][y+1];
                        greenValues[x][y] += greenValues[x][y+1];
                        blueValues[x][y] += blueValues[x][y+1];
                    }
                    //=========================
                    if ( mask[x][y-1] == INTERIOR_PIXEL ) {
                        redValues[x][y] += redValues[x][y-1];
                        greenValues[x][y] += greenValues[x][y-1];
                        blueValues[x][y] += blueValues[x][y-1];
                    }

                    redValues[x][y] = (minusWeight*oldRed) + weight*(redValues[x][y] * 0.25);
                    greenValues[x][y] = (minusWeight*oldGreen) + weight*(greenValues[x][y] * 0.25);
                    blueValues[x][y] = (minusWeight*oldBlue) + weight*(blueValues[x][y] * 0.25);

                }
            }
        }

    }

    private double calculateError() {

        double totalError = 0.0;

        double redError;
        double greenError;
        double blueError;

        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {

                    redError = gradientRed[x][y] - 4*redValues[x][y];
                    greenError = gradientGreen[x][y] - 4*greenValues[x][y];
                    blueError = gradientBlue[x][y] - 4*blueValues[x][y];

                    if ( mask[x+1][y] == INTERIOR_PIXEL ) {

                        redError += redValues[x+1][y];
                        greenError += greenValues[x+1][y];
                        blueError += blueValues[x+1][y];
                    }
                    //=========================
                    if ( mask[x-1][y] == INTERIOR_PIXEL ) {
                        redError += redValues[x-1][y];
                        greenError += greenValues[x-1][y];
                        blueError += blueValues[x-1][y];
                    }
                    //=========================
                    if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        redError += redValues[x][y+1];
                        greenError += greenValues[x][y+1];
                        blueError += blueValues[x][y+1];
                    }
                    //=========================
                    if ( mask[x][y-1] == INTERIOR_PIXEL ) {
                        redError += redValues[x][y-1];
                        greenError += greenValues[x][y-1];
                        blueError += blueValues[x][y-1];
                    }

                    totalError += ( (redError*redError) + (greenError*greenError) + (blueError*blueError) );
                }
            }
        }

        totalError = Math.sqrt( totalError );

        return totalError;

    }

    private void preProcess() {
        blendImageBounds = new ImageBoundingBox( imageWidth, imageHeight );
        initialiseDataStructures( imageWidth, imageHeight );
        firstPass();
        approximateLaplacian();
        //mixedGradients();
        initialGuess();
    }

    private void initialiseDataStructures( int p_imageWidth, int p_imageHeight ) {

        redValues = new double[p_imageWidth][p_imageHeight];
        greenValues = new double[p_imageWidth][p_imageHeight];
        blueValues = new double[p_imageWidth][p_imageHeight];

        redValuesGuess = new double[p_imageWidth][p_imageHeight];
        greenValuesGuess = new double[p_imageWidth][p_imageHeight];
        blueValuesGuess = new double[p_imageWidth][p_imageHeight];

        gradientRed = new double[p_imageWidth][p_imageHeight];
        gradientGreen = new double[p_imageWidth][p_imageHeight];
        gradientBlue = new double[p_imageWidth][p_imageHeight];

        mask = new short[p_imageWidth][p_imageHeight];
    }

    /**
     * 4 purposes:
     * <p>
     * 1) decompose image pixels into R, G, and B arrays <br>
     * 2) set border pixels, of the image to be blended, to value of background image<br>
     * 3) create "mask" of image. possible values: 1) transparent pixel, 2) border pixel, 3) interior pixel<br>
     * 4) find  minimum and maximum bounds of the image to be blended
     */
    private void firstPass() {
        for ( int x = 0; x < imageWidth; x++ ) {
            for ( int y = 0; y < imageHeight; y++ ) {
                if ( blendImageReader.getColor(x, y).equals(Color.TRANSPARENT) ) {
                    mask[x][y] = BACKGROUND_PIXEL;
                } else {
                    if ( isBorderPixel2(blendImageReader, x, y) ) {
                        //count++;
                        mask[x][y] = BORDER_PIXEL;
                        blendImageWriter.setArgb(x, y, backgroundImageReader.getArgb(x, y));

                        int argb = backgroundImageReader.getArgb(x, y);

                        int red = (argb >> 16) & 0xFF ;
                        int green = (argb >> 8) & 0xFF ;
                        int blue = argb & 0xFF ;

                        redValues[x][y] = red;
                        greenValues[x][y] = green;
                        blueValues[x][y] = blue;

//						redBackgroundValues[x][y] = red;
//						greenBackgroundValues[x][y] = green;
//						blueBackgroundValues[x][y] = blue;

                    } else {
                        //count++;
                        if ( blendImageBounds.xMin > x ) blendImageBounds.xMin = x;
                        if ( blendImageBounds.xMax < x ) blendImageBounds.xMax = x;
                        if ( blendImageBounds.yMin > y ) blendImageBounds.yMin = y;
                        if ( blendImageBounds.yMax < y ) blendImageBounds.yMax = y;
                        mask[x][y] = INTERIOR_PIXEL;
                        int argb = blendImageReader.getArgb(x, y);

                        int red = (argb >> 16) & 0xFF ;
                        int green = (argb >> 8) & 0xFF ;
                        int blue = argb & 0xFF ;

                        redValues[x][y] = red;
                        greenValues[x][y] = green;
                        blueValues[x][y] = blue;

//						int argbBackground = backgroundImageReader.getArgb(x, y);

//				        int redBackground = (argbBackground >> 16) & 0xFF ;
//				        int greenBackground = (argbBackground >> 8) & 0xFF ;
//				        int blueBackground = argbBackground & 0xFF ;

//				        redBackgroundValues[x][y] = redBackground;
//				        greenBackgroundValues[x][y] = greenBackground;
//				        blueBackgroundValues[x][y] = blueBackground;
                    }
                }
            }
        }
    }

    /**
     * Pick initial guess for solution.
     * <p>
     * Without some way to accurately guess, just set all pixels to some arbitrary value.
     */
    private void initialGuess() {

        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {

                    redValues[x][y] = 0;
                    greenValues[x][y] = 0;
                    blueValues[x][y] = 0;

                }
            }
        }

    }

    /**
     * An adapted finite differences approximation of laplacian.
     * <p>
     * Only calculated for interior points. If all neighbouring pixels are interior points, then normal laplacian.
     * If any neighbouring pixels are border points, do not calculate difference with regards to that neighbour but use its full value.
     * <p>
     * Laplacian of background only needs to be calculated if performing a "mixed gradients" blend, in which case at each pixel position,
     * the highest <i>absolute</i> value of foreground or background will be used in the blend.
     */
    private void approximateLaplacian() {
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {
                    //=========================
                    if ( mask[x+1][y] == BORDER_PIXEL ) {
                        gradientRed[x][y] += redValues[x+1][y];
                        gradientGreen[x][y] += greenValues[x+1][y];
                        gradientBlue[x][y] += blueValues[x+1][y];
                    } else if ( mask[x+1][y] == INTERIOR_PIXEL ) {
                        gradientRed[x][y] += redValues[x][y] - redValues[x+1][y];
                        gradientGreen[x][y] += greenValues[x][y] - greenValues[x+1][y];
                        gradientBlue[x][y] += blueValues[x][y] - blueValues[x+1][y];
                    }
                    //=========================
                    if ( mask[x-1][y] == BORDER_PIXEL ) {
                        gradientRed[x][y] += redValues[x-1][y];
                        gradientGreen[x][y] += greenValues[x-1][y];
                        gradientBlue[x][y] += blueValues[x-1][y];
                    } else if ( mask[x-1][y] == INTERIOR_PIXEL ) {
                        gradientRed[x][y] += redValues[x][y] - redValues[x-1][y];
                        gradientGreen[x][y] += greenValues[x][y] - greenValues[x-1][y];
                        gradientBlue[x][y] += blueValues[x][y] - blueValues[x-1][y];
                    }
                    //=========================
                    if ( mask[x][y+1] == BORDER_PIXEL ) {
                        gradientRed[x][y] += redValues[x][y+1];
                        gradientGreen[x][y] += greenValues[x][y+1];
                        gradientBlue[x][y] += blueValues[x][y+1];
                    } else if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        gradientRed[x][y] += redValues[x][y] - redValues[x][y+1];
                        gradientGreen[x][y] += greenValues[x][y] - greenValues[x][y+1];
                        gradientBlue[x][y] += blueValues[x][y] - blueValues[x][y+1];
                    }
                    //=========================
                    if ( mask[x][y-1] == BORDER_PIXEL ) {
                        gradientRed[x][y] += redValues[x][y-1];
                        gradientGreen[x][y] += greenValues[x][y-1];
                        gradientBlue[x][y] += blueValues[x][y-1];
                    } else if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        gradientRed[x][y] += redValues[x][y] - redValues[x][y-1];
                        gradientGreen[x][y] += greenValues[x][y] - greenValues[x][y-1];
                        gradientBlue[x][y] += blueValues[x][y] - blueValues[x][y-1];
                    }
                }
            }
        }
    }

    /**
     * An adapted finite differences approximation of laplacian.
     * <p>
     * Only calculated for interior points. If all neighbouring pixels are interior points, then normal laplacian.
     * If any neighbouring pixels are border points, do not calculate difference with regards to that neighbour but use its full value.
     * <p>
     * Laplacian of background only needs to be calculated if performing a "mixed gradients" blend, in which case at each pixel position,
     * the highest <i>absolute</i> value of foreground or background will be used in the blend.
     */
    private void approximateLaplacian( double[][] p_redGradient, double[][] p_redValues, double[][] p_greenGradient, double[][] p_greenValues, double[][] p_blueGradient, double[][] p_blueValues ) {
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {
                    //=========================
                    if ( mask[x+1][y] == BORDER_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x+1][y];
                        p_greenGradient[x][y] += p_greenValues[x+1][y];
                        p_blueGradient[x][y] += p_blueValues[x+1][y];
                    } else if ( mask[x+1][y] == INTERIOR_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x][y] - p_redValues[x+1][y];
                        p_greenGradient[x][y] += p_greenValues[x][y] - p_greenValues[x+1][y];
                        p_blueGradient[x][y] += p_blueValues[x][y] - p_blueValues[x+1][y];
                    }
                    //=========================
                    if ( mask[x-1][y] == BORDER_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x-1][y];
                        p_greenGradient[x][y] += p_greenValues[x-1][y];
                        p_blueGradient[x][y] += p_blueValues[x-1][y];
                    } else if ( mask[x-1][y] == INTERIOR_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x][y] - p_redValues[x-1][y];
                        p_greenGradient[x][y] += p_greenValues[x][y] - p_greenValues[x-1][y];
                        p_blueGradient[x][y] += p_blueValues[x][y] - p_blueValues[x-1][y];
                    }
                    //=========================
                    if ( mask[x][y+1] == BORDER_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x][y+1];
                        p_greenGradient[x][y] += p_greenValues[x][y+1];
                        p_blueGradient[x][y] += p_blueValues[x][y+1];
                    } else if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x][y] - p_redValues[x][y+1];
                        p_greenGradient[x][y] += p_greenValues[x][y] - p_greenValues[x][y+1];
                        p_blueGradient[x][y] += p_blueValues[x][y] - p_blueValues[x][y+1];
                    }
                    //=========================
                    if ( mask[x][y-1] == BORDER_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x][y-1];
                        p_greenGradient[x][y] += p_greenValues[x][y-1];
                        p_blueGradient[x][y] += p_blueValues[x][y-1];
                    } else if ( mask[x][y+1] == INTERIOR_PIXEL ) {
                        p_redGradient[x][y] += p_redValues[x][y] - p_redValues[x][y-1];
                        p_greenGradient[x][y] += p_greenValues[x][y] - p_greenValues[x][y-1];
                        p_blueGradient[x][y] += p_blueValues[x][y] - p_blueValues[x][y-1];
                    }
                }
            }
        }
    }

    private void mixedGradients() {
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {

//				if ( (Math.abs(gradientRedBackground[x][y]) + Math.abs(gradientGreenBackground[x][y]) + Math.abs(gradientBlueBackground[x][y]) ) > ( Math.abs(gradientRed[x][y]) + gradientGreen[x][y] + gradientBlue[x][y] ) ) {
//					gradientRed[x][y] = gradientRedBackground[x][y];
//					gradientGreen[x][y] = gradientGreenBackground[x][y];
//					gradientBlue[x][y] = gradientBlueBackground[x][y];
//				}

//				if ( Math.abs(gradientRedBackground[x][y]) > Math.abs(gradientRed[x][y]) ) gradientRed[x][y] = gradientRedBackground[x][y];
//				if ( Math.abs(gradientGreenBackground[x][y]) > Math.abs(gradientGreen[x][y]) ) gradientGreen[x][y] = gradientGreenBackground[x][y];
//				if ( Math.abs(gradientBlueBackground[x][y]) > Math.abs(gradientBlue[x][y]) ) gradientBlue[x][y] = gradientBlueBackground[x][y];

//				double l_alpha = 0.5;
//				gradientRed[x][y] = l_alpha*gradientRed[x][y] + (1-l_alpha)*gradientRedBackground[x][y];
//				gradientGreen[x][y] = l_alpha*gradientGreen[x][y] + (1-l_alpha)*gradientGreenBackground[x][y];
//				gradientBlue[x][y] = l_alpha*gradientBlue[x][y] + (1-l_alpha)*gradientBlueBackground[x][y];
                }
            }
        }
    }

    private boolean isBorderPixel( PixelReader p_pixelReader, int xCoord, int yCoord ) {

        if ( p_pixelReader.getColor(xCoord-1, yCoord).equals(Color.TRANSPARENT) || p_pixelReader.getColor(xCoord+1, yCoord).equals(Color.TRANSPARENT) || p_pixelReader.getColor(xCoord, yCoord+1).equals(Color.TRANSPARENT) || p_pixelReader.getColor(xCoord, yCoord-1).equals(Color.TRANSPARENT) ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isBorderPixel2( PixelReader p_pixelReader, int xCoord, int yCoord ) {

        if ( p_pixelReader.getArgb(xCoord-1, yCoord) == 0 || p_pixelReader.getArgb(xCoord+1, yCoord) == 0 || p_pixelReader.getArgb(xCoord, yCoord+1) == 0 || p_pixelReader.getArgb(xCoord, yCoord-1) == 0 ) {
            return true;
        } else {
            return false;
        }
    }

    private void normalizePixelValues() {
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {
                    if (redValues[x][y] < 0) {
                        redValues[x][y] = 0;
                    }
                    if (redValues[x][y] > 255) {
                        redValues[x][y] = 255;
                    }
                    if (greenValues[x][y] < 0) {
                        greenValues[x][y] = 0;
                    }
                    if (greenValues[x][y] > 255) {
                        greenValues[x][y] = 255;
                    }
                    if (blueValues[x][y] < 0) {
                        blueValues[x][y] = 0;
                    }
                    if (blueValues[x][y] > 255) {
                        blueValues[x][y] = 255;
                    }
                }
            }
        }
    }

    private void writePixelValues() {
        for ( int x = blendImageBounds.xMin - 1; x < blendImageBounds.xMax + 2; x++ ) {
            for ( int y = blendImageBounds.yMin - 1; y < blendImageBounds.yMax + 2; y++ ) {
                if ( mask[x][y] == INTERIOR_PIXEL ) {
                    int newColour = (0xFF000000 | ((int)redValues[x][y] << 16) | ((int)greenValues[x][y] << 8) | (int)blueValues[x][y]) ;
                    blendImageWriter.setArgb(x, y, newColour);
                }
            }
        }
    }

    private class ImageBoundingBox {
        public int xMin, xMax, yMin, yMax;

        public ImageBoundingBox( int p_imageWidth, int p_imageHeight ) {
            xMin = p_imageWidth;
            yMin = p_imageHeight;
            xMax = 0;
            yMax = 0;
        }
    }

}
