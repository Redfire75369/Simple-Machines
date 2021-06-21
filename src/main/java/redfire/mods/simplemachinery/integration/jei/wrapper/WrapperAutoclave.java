package redfire.mods.simplemachinery.integration.jei.wrapper;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import redfire.mods.simplemachinery.tileentities.autoclave.RecipeAutoclave;

public class WrapperAutoclave extends WrapperMachine<RecipeAutoclave> {
    public WrapperAutoclave(IJeiHelpers jeiHelpers, RecipeAutoclave recipe) {
        super(jeiHelpers, recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInputLists(VanillaTypes.ITEM, jeiHelpers.getStackHelper().expandRecipeItemStackInputs(recipe.inputs));
        ingredients.setInput(VanillaTypes.FLUID, recipe.fluidInputs.get(0));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.outputs.get(0));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }
}