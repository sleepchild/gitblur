package sleepviews;

import android.content.*;
import android.util.*;
import android.widget.*;
import android.graphics.drawable.*;
import android.graphics.*;
import sleepchild.gitblur.R;
import android.content.res.*;

public class RoundyButton extends Button
{
    public RoundyButton(Context ctx){
        super(ctx);
        init();
    }

    public RoundyButton(Context ctx, AttributeSet attrs){
        this(ctx, attrs,0);
        init();
    }

    public RoundyButton(Context ctx, AttributeSet attrs, int defStyles){
        super(ctx, attrs, defStyles);
        getattribs(attrs);
        init();
    }
    //////
    
    int backgroundColor = Color.TRANSPARENT;
    int borderColor = Color.TRANSPARENT;
    int borderWidth=5;
    int cornerRadius=20;
    float radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight ;
    float[] cornerRadii = new float[8];
    boolean useRadii=false;
    
    private void init(){
        //setTextAppearance(this.getContext(), android.R.style.TextAppearance_Medium);
        apply();
    }
    
    private void getattribs(AttributeSet attrs){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomViewItem,0,0);
        //
        backgroundColor = a.getColor(R.styleable.CustomViewItem_backgroundColor, backgroundColor);
        borderColor = a.getColor(R.styleable.CustomViewItem_borderColor, borderColor);
        borderWidth = a.getDimensionPixelSize(R.styleable.CustomViewItem_borderWidth, borderWidth);
        cornerRadius = a.getDimensionPixelSize(R.styleable.CustomViewItem_cornerRadius, cornerRadius);
        //
    }

    @Override
    public void setBackgroundColor(int color)
    {
        super.setBackgroundColor(color);
       // backgroundColor = color;
       // apply();
    }
    
    public void setBorderColor(int color){
        borderColor = color;
        apply();
    }
    
    public void setBorderWidth(int width){
        borderWidth = width;
        apply();
    }
    
    public void setCornerRadius(int radius){
        cornerRadius = radius;
        useRadii=false;
        apply();
    }
    
    public void setCornerRadii(float[] radii){
        cornerRadii = radii;
        useRadii=true;
        apply();
    }
    
    private void apply(){
        setClickable(true);
        GradientDrawable gd = new GradientDrawable();
        GradientDrawable pd = new GradientDrawable();
        //
        if(useRadii){
            gd.setCornerRadii(cornerRadii);
            pd.setCornerRadii(cornerRadii);
        }else{
            gd.setCornerRadius(cornerRadius);
            pd.setCornerRadius(cornerRadius);
        }
        gd.setColor(backgroundColor);
        gd.setStroke(borderWidth, borderColor);
        //
        pd.setColor(darken(backgroundColor, 0.1));
        pd.setStroke(borderWidth, darken(borderColor,0.1));
        //
        StateListDrawable sel = new StateListDrawable();
        sel.addState(new int[]{android.R.attr.state_pressed}, pd);
        sel.addState(new int[]{}, gd);
        //
        setBackground(sel);
    }
    
    private int lighten(int color){
        //
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        g=g/2;
        b=b/2;
        //
        return Color.argb(255, r,g,b);
    }
    
    public static int adjustBrightness(int color, float amount) {
        int red = color & 0xFF0000 >> 16;
        int green = color & 0x00FF00 >> 8;
        int blue = color & 0x0000FF;
        int result = (int)(blue * amount);
        result += (int)(green * amount) << 8;
        result += (int)(red * amount) << 16;
        return result;
    }
    //*
    public static int darken(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, fraction);
        green = darkenColor(green, fraction);
        blue = darkenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }
    private static int darkenColor(int color, double fraction) {
        return (int)Math.max(color - (color * fraction), 0);
    }
    //*/
    
}
