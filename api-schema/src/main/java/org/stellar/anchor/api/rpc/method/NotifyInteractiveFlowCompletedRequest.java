package org.stellar.anchor.api.rpc.method;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.stellar.anchor.api.rpc.method.features.SupportsUserActionRequiredBy;
import org.stellar.anchor.api.shared.FeeDetails;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotifyInteractiveFlowCompletedRequest extends RpcMethodParamsRequest
    implements SupportsUserActionRequiredBy {

  @NotNull
  @SerializedName("amount_in")
  private AmountAssetRequest amountIn;

  @NotNull
  @SerializedName("amount_out")
  private AmountAssetRequest amountOut;

  @SerializedName("fee_details")
  private FeeDetails feeDetails;

  @SerializedName("amount_expected")
  private AmountRequest amountExpected;

  @SerializedName("user_action_required_by")
  Instant userActionRequiredBy;
}
